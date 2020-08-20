<?php
const ARG_MAIL = 'mail';
const ARG_PASS = 'pass';
const ARG_IP   = 'ip';

interface UserInteface
{
//region SECTION: Getters/Setters
    public function getDomain();
//endregion Getters/Setters
}

class User implements UserInteface
{
//region SECTION: Fields
    const AUTH_LDAP = 'ldap';
    const AUTH_SQL  = 'sql';
    const AUTH_NONE = 'unauth';
    private $home;
    private $localPart;
    private $password;
    private $userName;
    private $domain;
    private $aliasUser;
    private $aliasPassword;

    private $authType = self::AUTH_NONE;
//endregion Fields

//region SECTION: Constructor
    public function __construct(stdClass $object)
    {
        $this->setHome($object->home)
            ->setLocalPart($object->local_part)
            ->setPassword($object->password)
            ->setUserName($object->username)
            ->setDomain($object->domain);
    }
//endregion Constructor

//region SECTION: Public
    public function comparePassword()
    {
        return $this->getAliasPassword() === $this->getPassword();
    }

    public function toStdOut()
    {
        echo $this->getHome().' '.$this->getUserName().' '.$this->getAliasPassword().' '.$this->getAuth().' '.$this->getAliasUser().PHP_EOL;
    }
//endregion Public

//region SECTION: Getters/Setters
    public function getHome()
    {
        return $this->home;
    }

    public function getLocalPart()
    {
        return $this->localPart;
    }

    public function getPassword()
    {
        return $this->password;
    }

    public function getUserName()
    {
        return $this->userName;
    }

    public function getDomain()
    {
        return $this->domain;
    }

    public function getAliasUser()
    {
        return $this->aliasUser;
    }

    public function getAliasPassword()
    {
        return $this->aliasPassword;
    }

    public function getAuth()
    {
        return $this->authType;
    }

    public function setAuthLdap()
    {
        $this->authType = self::AUTH_LDAP;

        return $this;
    }

    public function setAuthSql()
    {
        $this->authType = self::AUTH_SQL;

        return $this;
    }

    public function setHome($home)
    {
        $this->home = $home;

        return $this;
    }

    public function setLocalPart($localPart)
    {
        $this->localPart = $localPart;

        return $this;
    }

    public function setPassword($password)
    {
        $this->password = $password;

        return $this;
    }

    public function setUserName($userName)
    {
        $this->userName = $userName;

        return $this;
    }

    public function setDomain($domain)
    {
        $this->domain = $domain;

        return $this;
    }

    public function setAliasUser($aliasUser)
    {
        $this->aliasUser = $aliasUser;

        return $this;
    }

    public function setAliasPassword($aliasPassword)
    {
        $this->aliasPassword = $aliasPassword;

        return $this;
    }
//endregion Getters/Setters
}

final class Connection
{
//region SECTION: Fields
    private $url;
    private $port;
    //AD domain name second level
    private $prefixAuth;
//endregion Fields

//region SECTION: Constructor
    public function __construct(string $prefixAuth, string $url, string $port = "636")
    {
        $this->prefixAuth = $prefixAuth;
        $this->url        = 'ldap://'.$url;
        $this->port       = $port;
    }
//endregion Constructor

//region SECTION: Getters/Setters
    public function getUrl()
    {
        return $this->url;
    }

    public function getPort()
    {
        return $this->port;
    }

    public function getPrefixAuth()
    {
        return ($this->prefixAuth !== '') ? $this->prefixAuth.'.' : '';
    }
//endregion Getters/Setters
}

class Ldap
{
//region SECTION: Fields
    private const DEFAULT_DOMAIN = 'ite-ng.ru';
    private $connect   = false;
    private $ldapHosts = [];
    private $user;
    private $userAuthDomain;
//endregion Fields

//region SECTION: Constructor
    public function __construct()
    {
        $this->ldapHosts ["ite-ng.ru"] = [
            new Connection("", "172.16.45.12"),
            new Connection("", "172.20.1.6"),
            new Connection("", "172.20.1.20"),
        ];
        $this->ldapHosts ["kpsz.ru"]   = [
            new Connection("corp", "srv3.corp.kpsz.ru"),
        ];
        $this->ldapHosts ["ite-eg.ru"] = [
            new Connection("corp", "pdc.corp.ite-eg.ru"),
        ];
    }
//endregion Constructor

//region SECTION: Public
    public function checkIsLdapUser()
    {
        return $this->openLdapServer();
    }
//endregion Public

//region SECTION: Private
    private function openLdapServer()
    {
        $bind = false;
        foreach ($this->getConnections() as $connection) {
            $this->connect = ldap_connect($connection->getUrl(), $connection->getPort());
            if ($this->connect) {
                try {
//			var_dump($this->user->getLocalPart()."@".$connection->getPrefixAuth().$this->getUserAuthDomain());
//			var_dump($this->user->getAliasPassword());
                    $bind = @ldap_bind($this->connect, $this->user->getLocalPart()."@".$connection->getPrefixAuth().$this->getUserAuthDomain(), $this->user->getAliasPassword());
                } catch (\ErrorException $e) {
                    $bind = false;
                }
                if ($bind) {
                    $this->user->setAuthLdap();
                    break;
                }
            }
        }

        return $bind;
    }

    private function getUserAuthDomain()
    {
        if (!$this->userAuthDomain) {
            $this->userAuthDomain = array_key_exists($this->user->getDomain(), $this->ldapHosts) ? $this->user->getDomain() : self::DEFAULT_DOMAIN;
        }

        return $this->userAuthDomain;
    }

    private function resetUserAuthDomain()
    {
        $this->userAuthDomain = null;
    }

    private function getConnections()
    {
        return $this->ldapHosts[$this->getUserAuthDomain()];
    }
//endregion Private

//region SECTION: Getters/Setters
    public function setUser(UserInteface &$user)
    {
        $this->user = $user;
        $this->resetUserAuthDomain();

        return $this;
    }
//endregion Getters/Setters
}

class Checker
{
//region SECTION: Fields
    private $host     = '172.18.2.1';
    private $dbname   = 'postfix';
    private $userName = 'postfix';
    private $password = 'postfix';

    private $connect = false;

    private $user = false;
//endregion Fields

//region SECTION: Public
    public function checkUser($user, $pass)
    {
        $isAuth = false;
        if ($this->connection()) {
            $this->getMailUser($user, $pass);
            $this->connect->close();
            if ($this->user) {
                $ldap   = new Ldap();
                $isAuth = $ldap
                    ->setUser($this->user)
                    ->checkIsLdapUser();
                if (!$isAuth) {
                    $isAuth = $this->user->comparePassword();
                    if ($isAuth) {
                        $this->user->setAuthSql();
                    }
                }
            }
        }

        return $isAuth;
    }
//endregion Public

//region SECTION: Private
    private function getMailUser($aliasUser, $aliasPassword)
    {
        $sql = "SELECT CONCAT('/opt/WWW/container.ite-ng.ru/data/Mail/Domains/',m.domain,'/', m.username) as home, m.local_part, m.password, m.username, m.domain FROM `alias` a LEFT JOIN `mailbox` m on m.`username` = a.`goto` WHERE a.`address` LIKE '".$aliasUser."' and m.active = '1'";
        if ($result = $this->connect->query($sql)) {
            if ($obj = $result->fetch_object()) {
                $this->user = new User($obj);
                $this->user->setAliasUser($aliasUser)->setAliasPassword($aliasPassword);
            }
        }
    }

    private function connection()
    {
        $this->connect = new mysqli($this->host, $this->userName, $this->password, $this->dbname);
        if ($this->connect->connect_errno) {
            printf("You have a connection problems: %s\n", $this->connect->connect_error);

            return false;
        }

        return true;
    }
//endregion Private

//region SECTION: Getters/Setters
    public function getUser()
    {
        return $this->user;
    }
//endregion Getters/Setters

}

$options = getopt('', [ARG_MAIL.':', ARG_PASS.':', ARG_IP.':']);
if (array_key_exists(ARG_MAIL, $options) && array_key_exists(ARG_PASS, $options)) {
    $check = new Checker();
    if ($check->checkUser($options[ARG_MAIL], $options[ARG_PASS])) {
        $check->getUser()->toStdOut();
    }
}
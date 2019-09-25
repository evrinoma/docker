<?php
const ARG_MAIL = 'mail';
const ARG_PASS = 'pass';
const ARG_IP = 'ip';

class User
{
	const AUTH_LDAP = 'ldap';
	const AUTH_SQL = 'sql';
	const AUTH_NONE = 'unauth';
	private $home;
	private $localPart;
	private $password;
	private $userName;
	private $domain;
	private $aliasUser;
	private $aliasPassword;
	
	private $authType = self::AUTH_NONE;
	
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

	public function __construct(stdClass $object)
	{
	    $this->setHome($object->home)
		->setLocalPart($object->local_part)
		->setPassword($object->password)
		->setUserName($object->username)
		->setDomain($object->domain);
	}
	
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

	public function comparePassword()
	{
		return $this->getAliasPassword() ===  $this->getPassword();
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
	
	public function toStdOut()
	{
		echo $this->getHome().' '.$this->getUserName().' '.$this->getAliasPassword().' '.$this->getAuth().' '.$this->getAliasUser().PHP_EOL;
	}
}

class Ldap
{
    private $domain = 'ite-ng.ru';
    private $connect = false;
    private $ldapHosts = [
			'ldap://172.16.45.12' => ['port' => '636'],
			'ldap://172.20.1.6' => ['port' => '636'],
			'ldap://172.20.1.20' => ['port' => '636']
			];
    private $user;

    private function openLdapServer()
    {
	$bind = false;
        foreach ($this->ldapHosts as $key => $server) {
		$this->connect = ldap_connect($key, $server['port']);
		if ($this->connect){
			try {
			$bind = @ldap_bind($this->connect, $this->user->getLocalPart()."@".$this->domain, $this->user->getAliasPassword());
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
    
    public function setUser(&$user)
    {
	$this->user = $user;
	return $this;
    }
    
    public function checkIsLdapUser()
    {
	return $this->openLdapServer();
    }
}

class Checker 
{
    private $host='172.18.2.1';
    private $dbname='postfix';
    private $userName='postfix';
    private $password='postfix';
    
    private $connect = false;
    
    private $user = false;

    private function getMailUser($aliasUser, $aliasPassword)
    {
	$sql ="SELECT CONCAT('/opt/WWW/container.ite-ng.ru/data/Mail/Domains/',m.domain,'/', m.username) as home, m.local_part, m.password, m.username, m.domain FROM `alias` a LEFT JOIN `mailbox` m on m.`username` = a.`goto` WHERE a.`address` LIKE '".$aliasUser."' and m.active = '1'";
	if ($result = $this->connect->query($sql)) {
		if ($obj = $result->fetch_object()) {
		$this->user = new User($obj);
		$this->user->setAliasUser($aliasUser)->setAliasPassword($aliasPassword);
		}
	}
    }
    
    private function connection()
    {
	$this->connect = new mysqli($this->host,$this->userName, $this->password,$this->dbname);
	if ($this->connect->connect_errno) {
            printf("Не удалось подключиться: %s\n", $mysqli->connect_error);
	    return false;
	}
	return true;
    }
    
    private function ldap()
    {
    
    }
    
    public function checkUser($user,$pass)
    {
	$isAuth = false;
	if($this->connection())
	{
		$this->getMailUser($user,$pass);
		$this->connect->close();
		if ($this->user) {
			$ldap = new Ldap();
			$isAuth = $ldap
			    ->setUser($this->user)
			    ->checkIsLdapUser();
			if(!$isAuth) {
				$isAuth = $this->user->comparePassword();
				if ($isAuth) {
					$this->user->setAuthSql();
				}
			}
		}
	}
	
	return $isAuth;
    }
    
    public function getUser()
    {
	return $this->user;
    }
    
}
$options=getopt('',[ ARG_MAIL.':',ARG_PASS.':',ARG_IP.':']);
if (array_key_exists(ARG_MAIL, $options) && array_key_exists(ARG_PASS, $options)) {
	$check = new Checker();
	if ($check->checkUser($options[ ARG_MAIL],$options[ ARG_PASS])){
		$check->getUser()->toStdOut();
	}
}
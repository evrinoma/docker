how to install
ssh root@172.18.2.1
init.install $dbpass

ssh root@172.18.4.2
init.openssl $sslpass
http://172.16.45.42:8010/setup.php
change password and configure config.inc.php 

ssh root@172.18.72.11
init.install $dbpass https://github.com/roundcube/roundcubemail/releases/download/1.3.10/roundcubemail-1.3.10-complete.tar.gz

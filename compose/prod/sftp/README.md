how to install
ssh root@172.18.2.1
create.user $dbpass

ssh root@172.18.72.14
init.install $dbpass $owncloudrootpass
init.openssl $sslpass
add new host /opt/WWW/container.ite-ng.ru/projects/$projectName/config/config.php 

ssh root@172.18.72.21
init.install $dbpass $owncloudrootpass
init.openssl $sslpass
add new host /opt/WWW/container.ite-ng.ru/projects/$projectName/config/config.php 


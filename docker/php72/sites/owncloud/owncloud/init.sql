#!/bin/bash
pass=$1
serverSql=$2
INIT_SQL="/tmp/init.sql"
	echo "
	CREATE DATABASE owncloud;
	CREATE USER 'owncloud'@'localhost' IDENTIFIED BY 'owncloud';
	GRANT ALL PRIVILEGES ON \`owncloud\` . * TO 'owncloud'@'localhost';
	CREATE USER 'owncloud'@'172.18.0.0/255.255.0.0' IDENTIFIED BY 'owncloud';
	GRANT ALL PRIVILEGES ON \`owncloud\` . * TO 'owncloud'@'172.18.0.0/255.255.0.0';
	FLUSH PRIVILEGES;
	" > $INIT_SQL
	mariaInstall=`yum list installed | grep "mariadb\."`
	if [ ! -z "mariaInstall" ]; then 
		yum install mysql -y
		mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
		yum remove mysql -y
	else
		mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
	fi
	rm -f $INIT_SQL

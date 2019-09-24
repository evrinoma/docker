#!/bin/bash
pass=$1
serverSql=$2
INIT_SQL="/tmp/init.sql"
	echo "
	CREATE DATABASE postfix;
	CREATE USER 'postfix'@'localhost' IDENTIFIED BY 'postfix';
	GRANT ALL PRIVILEGES ON \`postfix\` . * TO 'postfix'@'localhost';
	CREATE USER 'postfix'@'172.18.0.0/255.255.0.0' IDENTIFIED BY 'postfix';
	GRANT ALL PRIVILEGES ON \`postfix\` . * TO 'postfix'@'172.18.0.0/255.255.0.0';
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

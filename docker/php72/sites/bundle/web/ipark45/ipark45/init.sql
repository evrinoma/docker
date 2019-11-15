#!/bin/bash
pass=$1
serverSql=$2
base=$3
user=$4
basepass=$5
INIT_SQL="/tmp/init.sql"
SERVER_SQL="/tmp/site_ipark45_ru_new_db.sql"
	echo "
	CREATE DATABASE \`$base\`;
	CREATE USER '$user'@'localhost' IDENTIFIED BY '$basepass';
	GRANT ALL PRIVILEGES ON \`$user\` . * TO '$user'@'localhost';
	CREATE USER '$user'@'172.18.0.0/255.255.0.0' IDENTIFIED BY '$basepass';
	GRANT ALL PRIVILEGES ON \`$base\` . * TO '$user'@'172.18.0.0/255.255.0.0';
	FLUSH PRIVILEGES;
	" > $INIT_SQL
	mariaInstall=`yum list installed | grep "mariadb\."`
	if [ ! -z "mariaInstall" ]; then 
		yum install mysql -y
		mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
		mysql -h$serverSql -uroot -p$pass $base < $SERVER_SQL
		yum remove mysql -y
	else
		mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
		mysql -h$serverSql -uroot -p$pass $base < $SERVER_SQL
	fi
	rm -f $INIT_SQL
	rm -f $SERVER_SQL

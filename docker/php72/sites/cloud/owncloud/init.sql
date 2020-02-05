#!/bin/bash
pass=$1
serverSql=$2
base=$3
user=$4
basepass=$5
INIT_SQL="/tmp/init.sql"
	echo "
	CREATE DATABASE \`$base\`;
	CREATE USER '$user'@'localhost' IDENTIFIED BY '$basepass';
	GRANT ALL PRIVILEGES ON \`$user\` . * TO '$user'@'localhost';
	CREATE USER '$user'@'172.18.0.0/255.255.0.0' IDENTIFIED BY '$basepass';
	GRANT ALL PRIVILEGES ON \`$base\` . * TO '$user'@'172.18.0.0/255.255.0.0';
	FLUSH PRIVILEGES;
	" > $INIT_SQL
	mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
	rm -f $INIT_SQL

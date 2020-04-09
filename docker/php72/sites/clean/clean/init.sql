#!/bin/bash
pass=$1
serverSql=$2
base=$3
user=$4
basepass=$5
INIT_SQL="/root/init.sql"
	echo "
	CREATE USER '$user'@'localhost' IDENTIFIED BY '$basepass';
	GRANT ALL PRIVILEGES ON \`$user\` . * TO '$user'@'localhost';
	CREATE USER '$user'@'%' IDENTIFIED BY '$basepass';
	GRANT ALL PRIVILEGES ON \`$base\` . * TO '$user'@'%';
	FLUSH PRIVILEGES;
	" > $INIT_SQL
	mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
	rm -f $INIT_SQL

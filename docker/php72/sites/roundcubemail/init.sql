#!/bin/bash
	pass=$1
	serverSql=$2
	DIR=$3
	INIT_SQL="/tmp/init.sql"
	cd $DIR
	echo "
	CREATE DATABASE \`roundcubemail\`;
	CREATE USER 'roundcubemail'@'172.18.0.0/255.255.0.0' IDENTIFIED BY 'roundcubemail';
	GRANT ALL PRIVILEGES ON \`roundcubemail\` . * TO 'roundcubemail'@'172.18.0.0/255.255.0.0';
	" > $INIT_SQL
	mariaInstall=`yum list installed | grep "mariadb\."`
	if [ ! -z "mariaInstall" ]; then 
		yum install mysql -y
		mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
		mysql -h$serverSql -uroot -p$pass roundcubemail < /opt/WWW/container.ite-ng.ru/projects/roundcubemail/SQL/mysql.initial.sql
		yum remove mysql -y
	else
		mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
		mysql -h$serverSql -uroot -p$pass roundcubemail < /opt/WWW/container.ite-ng.ru/projects/roundcubemail/SQL/mysql.initial.sql
	fi
	rm -f $INIT_SQL

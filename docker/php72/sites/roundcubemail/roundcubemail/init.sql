 #!/bin/bash
	pass=$1
	serverSql=$2
	DIR=$3
	INIT_SQL="/tmp/init.sql"
	cd $DIR
	echo "CREATE DATABASE \`roundcubemail_VERSION\`;" > $INIT_SQL
	user=$(mysql -h$serverSql -uroot -p$pass -e "SELECT user FROM mysql.user WHERE user = 'roundcubemail';" | grep user)
    if [ -z $user ]; then
        echo "CREATE USER 'roundcubemail'@'172.18.0.0/255.255.0.0' IDENTIFIED BY 'roundcubemail';" >> $INIT_SQL
    fi
    echo "GRANT ALL PRIVILEGES ON \`roundcubemail_VERSION\`. * TO 'roundcubemail'@'172.18.0.0/255.255.0.0';" >> $INIT_SQL
	mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
	mysql -h$serverSql -uroot -p$pass roundcubemail_VERSION < /opt/WWW/container.ite-ng.ru/projects/roundcubemail/VERSION/SQL/mysql.initial.sql
	#rm -f $INIT_SQL

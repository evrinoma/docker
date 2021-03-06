#!/bin/bash
pass=$1
serverSql=$2
base=$3
user=$4
basepass=$5
INIT_SQL="/tmp/init.sql"
	echo "
	INSERT INTO \`$base\`.\`oc_appconfig\` (\`appid\`, \`configkey\`, \`configvalue\`) VALUES
	('core', 'enable_external_storage', 'yes'),
	('files_external', 'allow_user_mounting', 'yes'),
	('files_external', 'user_mounting_backends', 'sftp,\\\OC\\\Files\\\Storage\\\SFTP_Key');
	" > $INIT_SQL
	mysql -h$serverSql -uroot -p$pass  < $INIT_SQL
	rm -f $INIT_SQL

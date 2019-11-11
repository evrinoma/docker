#!/bin/bash
pass=$1
serverSql=$2
base=$3
INIT_SQL="/tmp/init.sql"
	echo "
	INSERT INTO \`$base\`.\`oc_appconfig\` (\`appid\`, \`configkey\`, \`configvalue\`) VALUES
		('user_ldap', 'has_memberof_filter_support', '1'),
		('user_ldap', 'home_folder_naming_rule', ''),
		('user_ldap', 'last_jpegPhoto_lookup', '0'),
		('user_ldap', 'ldap_agent_password', 'bGRhcA=='),
		('user_ldap', 'ldap_attributes_for_group_search', ''),
		('user_ldap', 'ldap_attributes_for_user_search', ''),
		('user_ldap', 'ldap_backup_host', ''),
		('user_ldap', 'ldap_backup_port', ''),
		('user_ldap', 'ldap_base', 'OU=MSK,DC=ite-ng,DC=ru'),
		('user_ldap', 'ldap_base_groups', 'OU=MSK,DC=ite-ng,DC=ru'),
		('user_ldap', 'ldap_base_users', 'OU=MSK,DC=ite-ng,DC=ru'),
		('user_ldap', 'ldap_cache_ttl', '600'),
		('user_ldap', 'ldap_configuration_active', '1'),
		('user_ldap', 'ldap_display_name', 'displayname'),
		('user_ldap', 'ldap_dn', 'CN=ldap,CN=Users,DC=ite-ng,DC=ru'),
		('user_ldap', 'ldap_dynamic_group_member_url', ''),
		('user_ldap', 'ldap_email_attr', ''),
		('user_ldap', 'ldap_experienced_admin', '1'),
		('user_ldap', 'ldap_expert_username_attr', ''),
		('user_ldap', 'ldap_expert_uuid_group_attr', ''),
		('user_ldap', 'ldap_expert_uuid_user_attr', 'objectguid'),
		('user_ldap', 'ldap_group_display_name', 'cn'),
		('user_ldap', 'ldap_group_filter', ''),
		('user_ldap', 'ldap_group_filter_mode', '0'),
		('user_ldap', 'ldap_group_member_assoc_attribute', 'uniqueMember'),
		('user_ldap', 'ldap_groupfilter_groups', ''),
		('user_ldap', 'ldap_groupfilter_objectclass', ''),
		('user_ldap', 'ldap_host', 'ldap://172.20.1.6'),
		('user_ldap', 'ldap_login_filter', '(samaccountname=%uid)'),
		('user_ldap', 'ldap_login_filter_mode', '0'),
		('user_ldap', 'ldap_loginfilter_attributes', 'cn'),
		('user_ldap', 'ldap_loginfilter_email', '0'),
		('user_ldap', 'ldap_loginfilter_username', '1'),
		('user_ldap', 'ldap_nested_groups', '0'),
		('user_ldap', 'ldap_override_main_server', ''),
		('user_ldap', 'ldap_paging_size', '500'),
		('user_ldap', 'ldap_port', '389'),
		('user_ldap', 'ldap_quota_attr', ''),
		('user_ldap', 'ldap_quota_def', '50G'),
		('user_ldap', 'ldap_tls', '0'),
		('user_ldap', 'ldap_turn_off_cert_check', '0'),
		('user_ldap', 'ldap_user_display_name_2', ''),
		('user_ldap', 'ldap_user_filter_mode', '0'),
		('user_ldap', 'ldap_user_name', 'samaccountname'),
		('user_ldap', 'ldap_userfilter_groups', ''),
		('user_ldap', 'ldap_userfilter_objectclass', 'organizationalPerson'),
		('user_ldap', 'ldap_userlist_filter', '(&(objectclass=organizationalperson)(!(!(mail=*)))(!(telephonenumber=null)))'),
		('user_ldap', 'signed', 'true'),
		('user_ldap', 'use_memberof_to_detect_membership', '1');
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

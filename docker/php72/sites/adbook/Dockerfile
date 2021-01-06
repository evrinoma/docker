FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 adbook" \
	image="php72.adbook" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

ARG	VHOST=''
ARG	database_host=''
ARG	database_port=''
ARG	database_name=''
ARG	database_user=''
ARG	database_password=''

RUN	echo "MODE=${MODE}" > /etc/sysconfig/vhost && \
	echo "VHOST=${VHOST}" >> /etc/sysconfig/vhost && \
	echo "database_host=${database_host}" >> /etc/sysconfig/vhost && \
	echo "database_port=${database_port}" >> /etc/sysconfig/vhost && \
	echo "database_name=${database_name}" >> /etc/sysconfig/vhost && \
	echo "database_user=${database_user}" >> /etc/sysconfig/vhost && \
	echo "database_password=${database_password}" >> /etc/sysconfig/vhost

COPY	adbook.conf /etc/httpd/conf.d/vhost.conf

ADD	init.container.cron.part /usr/local/bin
ADD	adbook/init.install /usr/local/bin
ADD	adbook/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	cat /usr/local/bin/init.container.cron.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.cron.part && \
    chmod 700 /usr/local/bin/init.container

RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf && \
	sed -i -e "s@VHOST@${VHOST}@g" /usr/local/bin/init.container

ADD	supervisor.conf /etc/supervisord.d/cron.conf
RUN	echo "0 0  *  *  * /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/httpd/${VHOST}/bin/console legio:webldap:update_from_ldap > /dev/null 2>&1" >> /etc/crontab.apache && \
	crontab -u apache /etc/crontab.apache && \
	crontab -u apache -l

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

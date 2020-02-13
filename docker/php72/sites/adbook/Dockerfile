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

COPY	adbook.conf /etc/httpd/conf.d/vhost.conf

ADD	init.container.cron.part /usr/local/bin
ADD	adbook/init.install /usr/local/bin
ADD	adbook/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	cat /usr/local/bin/init.container.cron.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.cron.part && \
    chmod 700 /usr/local/bin/init.container

ADD	supervisor.conf /etc/supervisord.d/cron.conf
RUN	echo "0 0  *  *  * /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/adbook/bin/console legio:webldap:update_from_ldap > /dev/null 2>&1" >> /etc/crontab.apache && \
	crontab -u apache /etc/crontab.apache && \
	crontab -u apache -l

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

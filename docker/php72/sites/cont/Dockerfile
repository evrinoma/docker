FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 cont" \
	image="php72.cont" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

COPY	cont.conf /etc/httpd/conf.d/vhost.conf

ADD	cont/init.install /usr/local/bin
ADD	cont/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

RUN	rm -rf /etc/php.d/xdebug.ini

ADD	supervisor.conf /etc/supervisord.d/cron.conf
#RUN	echo "0 0  *  *  * /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/cont/bin/console legio:webldap:update_from_ldap > /dev/null 2>&1" >> /etc/crontab.apache && \
#	crontab -u apache /etc/crontab.apache && \
#	crontab -u apache -l

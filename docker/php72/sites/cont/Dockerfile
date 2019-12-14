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

ADD	init.container.part /usr/local/bin
ADD	cont/init.install /usr/local/bin
ADD	cont/init.sql /usr/local/bin
ADD	reconfig /usr/local/bin/
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql && \
	chmod +x /usr/local/bin/reconfig && \
	cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part && \
	chmod 700 /usr/local/bin/init.container

ADD	supervisor.conf /etc/supervisord.d/cron.conf
RUN	echo "*/30    *       *       *       *       /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/cont/bin/console legio:cont:delivery_load_command --env=prod > /dev/null 2>&1" >> /etc/crontab.apache && \
	crontab -u apache /etc/crontab.apache && \
	crontab -u apache -l

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

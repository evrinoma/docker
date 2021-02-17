FROM \
	evrinoma/php53:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.3 kis.ite-ng.ru" \
	image="php53.ite-ng.ru" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-06"

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

COPY	init.container.part /usr/local/bin
COPY	init.container.cron.part /usr/local/bin
RUN cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part && \
	cat /usr/local/bin/init.container.cron.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.cron.part && \
	chmod 700 /usr/local/bin/init.container

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi
RUN	if [ ! -z "${VHOST}" ]; then echo "host=${VHOST}" > /etc/sysconfig/settings ; fi

COPY	kis.conf /etc/httpd/conf.d/vhost.conf
RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf && \
    sed -i -e "s@VHOST@${VHOST}@g" /usr/local/bin/init.container

#supervisor include does't work
COPY	supervisor.conf /etc/supervisord.d/cron.conf
RUN	cat /etc/supervisord.d/cron.conf >> /etc/supervisord.conf


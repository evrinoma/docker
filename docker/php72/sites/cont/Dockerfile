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

ARG	VHOST=''
ARG	database_host=''
ARG	database_port=''
ARG	database_name=''
ARG	database_user=''
ARG	database_password=''

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_10.x | bash -  && \
	yum install -y nodejs yarn

RUN	echo "MODE=${MODE}" > /etc/sysconfig/vhost && \
	echo "VHOST=${VHOST}" >> /etc/sysconfig/vhost && \
	echo "database_host=${database_host}" >> /etc/sysconfig/vhost && \
	echo "database_port=${database_port}" >> /etc/sysconfig/vhost && \
	echo "database_name=${database_name}" >> /etc/sysconfig/vhost && \
	echo "database_user=${database_user}" >> /etc/sysconfig/vhost && \
	echo "database_password=${database_password}" >> /etc/sysconfig/vhost

COPY	cont.conf /etc/httpd/conf.d/vhost.conf

COPY	init.container.cron.part /usr/local/bin
COPY	cont/init.install /usr/local/bin
COPY	cont/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	cat /usr/local/bin/init.container.cron.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.cron.part && \
	chmod 700 /usr/local/bin/init.container

RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf && \
	sed -i -e "s@VHOST@${VHOST}@g" /usr/local/bin/init.container

COPY	supervisor.conf /etc/supervisord.d/cron.conf
#RUN	echo "*/30    *       *       *       *       /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/httpd/cont/${VHOST}/bin/console legio:cont:delivery_load_command --env=prod > /dev/null 2>&1" >> /etc/crontab.apache && \
#	crontab -u apache /etc/crontab.apache && \
#	crontab -u apache -l

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi


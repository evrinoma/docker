FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 cont.api" \
	image="php74.cont.api" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-11-27"

ARG	VHOST=''
ARG	database_host=''
ARG	database_port=''
ARG	database_name=''
ARG	database_user=''
ARG	database_password=''
ARG	CERTS=''

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_14.x | bash -  && \
	yum install -y nodejs yarn

RUN	echo "MODE=${MODE}" > /etc/sysconfig/vhost && \
	echo "VHOST=${VHOST}" >> /etc/sysconfig/vhost && \
	echo "database_host=${database_host}" >> /etc/sysconfig/vhost && \
	echo "database_port=${database_port}" >> /etc/sysconfig/vhost && \
	echo "database_name=${database_name}" >> /etc/sysconfig/vhost && \
	echo "database_user=${database_user}" >> /etc/sysconfig/vhost && \
	echo "database_password=${database_password}" >> /etc/sysconfig/vhost && \
	echo "CERTS=${CERTS}" >> /etc/sysconfig/vhost

COPY	cont.api.conf /etc/httpd/conf.d/vhost.conf

COPY	init.container.cron.part /usr/local/bin

RUN	cat /usr/local/bin/init.container.cron.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.cron.part && \
	chmod 700 /usr/local/bin/init.container

RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf && \
	sed -i -e "s@VHOST@${VHOST}@g" /usr/local/bin/init.container

COPY	supervisor.conf /etc/supervisord.d/cron.conf

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi
RUN	if [ ! -z "${CERTS}" ] ; then ln -s ${CERTS} /etc/pki ; fi



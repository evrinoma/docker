FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 pmc" \
	image="php72.pmc" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

ARG	VHOST=''
ARG	database_host=''
ARG	database_port=''
ARG	database_name=''
ARG	database_user=''
ARG	database_password=''

ADD	pmc/init.install /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

RUN	echo "MODE=${MODE}" > /etc/sysconfig/vhost && \
   	echo "VHOST=${VHOST}" > /etc/sysconfig/vhost && \
	echo "database_host=${database_host}" >> /etc/sysconfig/vhost && \
	echo "database_port=${database_port}" >> /etc/sysconfig/vhost && \
	echo "database_name=${database_name}" >> /etc/sysconfig/vhost && \
	echo "database_user=${database_user}" >> /etc/sysconfig/vhost && \
	echo "database_password=${database_password}" >> /etc/sysconfig/vhost

COPY	pmc.conf /etc/httpd/conf.d/vhost.conf
RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

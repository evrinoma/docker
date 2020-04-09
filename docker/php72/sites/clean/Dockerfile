FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 clean" \
	image="php72.cont" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-03-02"

ARG	VHOST=''
ARG	database_host=''
ARG	database_port=''
ARG	database_name=''
ARG	database_user=''
ARG	database_password=''
ARG	RUN=''

RUN	echo "MODE=${MODE}" > /etc/sysconfig/vhost && \
	echo "VHOST=${VHOST}" >> /etc/sysconfig/vhost && \
	echo "database_host=${database_host}" >> /etc/sysconfig/vhost && \
	echo "database_port=${database_port}" >> /etc/sysconfig/vhost && \
	echo "database_name=${database_name}" >> /etc/sysconfig/vhost && \
	echo "database_user=${database_user}" >> /etc/sysconfig/vhost && \
	echo "database_password=${database_password}" >> /etc/sysconfig/vhost

COPY	clean.conf /etc/httpd/conf.d/vhost.conf

ADD	clean/init.install /usr/local/bin
ADD	clean/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf
RUN	sed -i -e "s@Require\ ip\ 172\.20\.1\.0\/24\ 172\.18\.0\.0\/16\ 127\.0\.0\.1@Require\ all\ granted@" /etc/httpd/conf.d/phpMyAdmin.conf && \
    sed -i -e "s@Allow\ from\ 172\.20\.1\.0\/24\ 172\.18\.0\.0\/16\ 127\.0\.0\.1@Allow\ from\ all@" /etc/httpd/conf.d/phpMyAdmin.conf && \
    sed -i -e "s@172\.18\.2\.1@maria\.db\.public@" /etc/phpMyAdmin/config.inc.php

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

RUN	if [ ! -z "${RUN}" ]; then echo "sleep 10 && ${RUN}" >> /usr/local/bin/init.container ; fi

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_10.x | bash -  && \
	yum install -y nodejs yarn

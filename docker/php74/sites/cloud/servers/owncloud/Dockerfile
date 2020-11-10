FROM \
	evrinoma/php74.cloud:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 owncloud" \
	image="php74.owncloud" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-03"

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

COPY	install/owncloud /etc/sysconfig/owncloud

RUN	sed -i '/###CLOUD_INSERT/r /etc/sysconfig/owncloud' /usr/local/bin/init.install && \
	sed -i '/###CLOUD_INSERT/r /etc/sysconfig/owncloud' /usr/local/bin/init.container && \
    sed -i '/###CLOUD_INSERT/r /etc/sysconfig/owncloud' /usr/local/bin/init.openssl && \
    sed -i '/###CLOUD_INSERT/r /etc/sysconfig/owncloud' /usr/local/bin/create.struct

RUN /usr/local/bin/create.struct

COPY	owncloud.conf /etc/httpd/conf.d/vhost.conf

#RUN	chmod -R 640 /tmp && \
#	chmod -R 640 /var/tmp

EXPOSE 443

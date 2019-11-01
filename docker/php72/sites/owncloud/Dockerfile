FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 owncloud" \
	image="php72.cloud" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-01"

ADD	owncloud/init.install /usr/local/bin
ADD	owncloud/init.sql /usr/local/bin
ADD	owncloud/init.conf /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql && \
	chmod +x /usr/local/bin/init.conf

COPY	owncloud.conf /etc/httpd/conf.d/vhost.conf

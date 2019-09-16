FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 postfixadmin" \
	image="php-7.2" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-09-16"

COPY	postfixadmin.conf /etc/httpd/conf.d/vhost.conf

ADD	init.postfixadmin /usr/local/bin
RUN	chmod +x /usr/local/bin/init.postfixadmin

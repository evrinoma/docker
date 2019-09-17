FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 roundcubemail" \
	image="php-7.2" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-09-17"

COPY	roundcubemail.conf /etc/httpd/conf.d/vhost.conf
COPY	ite-ng.ru /opt/WWW/ite-ng.ru

ADD	init.roundcubemail /usr/local/bin
RUN	chmod +x /usr/local/bin/init.roundcubemail
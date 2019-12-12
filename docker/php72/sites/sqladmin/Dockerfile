FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 sqladmin" \
	image="php72.sqladmin" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

COPY	sqladmin.conf /etc/httpd/conf.d/vhost.conf
RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

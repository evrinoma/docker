FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 sqladmin" \
	image="php74.sqladmin" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-03-02"

RUN sed -i -e "s@.*\;httpd autostart@autostart=true@" /etc/supervisord.d/supervisor.conf

COPY	sqladmin.conf /etc/httpd/conf.d/vhost.conf

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

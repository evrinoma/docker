FROM \
	evrinoma/php53:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.3 kis.ite-ng.ru" \
	image="php53.ite-ng.ru" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

RUN mkdir -p /opt/exchange1C

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi
#RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

COPY	kis.ite-ng.ru.conf /etc/httpd/conf.d/vhost.conf


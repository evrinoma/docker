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

ARG	VHOST=''
ADD	init.container.part /usr/local/bin
RUN cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi
RUN	if [ ! -z "${VHOST}" ]; then echo "host=${VHOST}" > /etc/sysconfig/settings ; fi

COPY	kis.ite-ng.ru.conf /etc/httpd/conf.d/vhost.conf
RUN	sed -i -e "s@VHOST@${VHOST}@g" /etc/httpd/conf.d/vhost.conf && \
    sed -i -e "s@VHOST@${VHOST}@g" /usr/local/bin/init.container



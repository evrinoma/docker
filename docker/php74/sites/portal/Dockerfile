FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 portal" \
	image="php74.portal" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-03-02"

COPY	install/portal.sql.tar.gz /root/
COPY	install/portal.tar.gz /root/
ADD	portal/init.install /usr/local/bin
ADD	portal/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

RUN sed -i -e "s@.*\;httpd autostart@autostart=true@" /etc/supervisord.d/supervisor.conf

COPY	portal.conf /etc/httpd/conf.d/vhost.conf

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

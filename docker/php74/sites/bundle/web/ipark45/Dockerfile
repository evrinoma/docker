FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 ipark45" \
	image="php74.ipark45" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/ipark45.sql.tar.gz /root/src/
COPY	install/ipark45.tar.gz /root/src/
COPY	ipark45/init.install /usr/local/bin
COPY	ipark45/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

RUN sed -i -e "s@.*\;httpd autostart@autostart=true@" /etc/supervisord.d/supervisor.conf

COPY	ipark45.conf /etc/httpd/conf.d/vhost.conf

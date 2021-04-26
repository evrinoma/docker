FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 kzkt45" \
	image="php74.kzkt45" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/kzkt45.sql.tar.gz /root/src/
COPY	install/kzkt45.tar.gz /root/src/
COPY	kzkt45/init.install /usr/local/bin
COPY	kzkt45/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

RUN sed -i -e "s@.*\;httpd autostart@autostart=true@" /etc/supervisord.d/supervisor.conf

COPY	kzkt45.conf /etc/httpd/conf.d/vhost.conf

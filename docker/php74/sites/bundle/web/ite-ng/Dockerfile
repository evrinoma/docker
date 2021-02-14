FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 ite-ng" \
	image="php74.ite-ng" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/ite-ng.sql.tar.gz /root/src
COPY	install/ite-ng.tar.gz /root/src
COPY	ite-ng/init.install /usr/local/bin
COPY	ite-ng/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	ite-ng.conf /etc/httpd/conf.d/vhost.conf

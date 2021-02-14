FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 vargashi45" \
	image="php74.vargashi45" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/vargashi45.sql.tar.gz /root/src/
COPY	install/vargashi45.tar.gz /root/src/
COPY	vargashi45/init.install /usr/local/bin
COPY	vargashi45/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	vargashi45.conf /etc/httpd/conf.d/vhost.conf

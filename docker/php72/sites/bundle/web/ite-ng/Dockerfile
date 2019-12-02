FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 ite-ng" \
	image="php72.ite-ng" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/ite-ng.sql.tar.gz /root/
COPY	install/ite-ng.tar.gz /root/
ADD	ite-ng/init.install /usr/local/bin
ADD	ite-ng/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

COPY	ite-ng.conf /etc/httpd/conf.d/vhost.conf

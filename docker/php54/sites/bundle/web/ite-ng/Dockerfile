FROM \
	evrinoma/php54:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.4 ite-ng" \
	image="php54.ite-ng" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-18"

COPY	install/ite-ng.sql.tar.gz /root/
COPY	install/ite-ng.tar.gz /root/
ADD	ite-ng/init.install /usr/local/bin
ADD	ite-ng/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

COPY	ite-ng.conf /etc/httpd/conf.d/vhost.conf

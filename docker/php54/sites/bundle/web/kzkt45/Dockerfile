FROM \
	evrinoma/php54:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.4 kzkt45" \
	image="php54.kzkt45" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-18"

COPY	install/kzkt45.sql.tar.gz /root/
COPY	install/kzkt45.tar.gz /root/
ADD	kzkt45/init.install /usr/local/bin
ADD	kzkt45/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

COPY	kzkt45.conf /etc/httpd/conf.d/vhost.conf

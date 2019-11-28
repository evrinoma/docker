FROM \
	evrinoma/php54:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.4 nekeng" \
	image="php54.nekeng" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-18"

COPY	install/nekeng.sql.tar.gz /tmp/
COPY	install/nekeng.tar.gz /tmp/
ADD	nekeng/init.install /usr/local/bin
ADD	nekeng/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

COPY	nekeng.conf /etc/httpd/conf.d/vhost.conf

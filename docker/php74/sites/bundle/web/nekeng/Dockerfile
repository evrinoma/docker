FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 nekeng" \
	image="php74.nekeng" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/nekeng.sql.tar.gz /root/src
COPY	install/nekeng.tar.gz /root/src
COPY	nekeng/init.install /usr/local/bin
COPY	nekeng/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	nekeng.conf /etc/httpd/conf.d/vhost.conf

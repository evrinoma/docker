FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 kpsz" \
	image="php74.kpsz" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/kpsz.sql.tar.gz /root/src
COPY	install/kpsz.tar.gz /root/src
COPY	kpsz/init.install /usr/local/bin
COPY	kpsz/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	kpsz.conf /etc/httpd/conf.d/vhost.conf

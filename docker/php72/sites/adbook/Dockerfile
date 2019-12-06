FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 webldap5" \
	image="php72.webldap5" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

COPY	adbook.conf /etc/httpd/conf.d/vhost.conf

ADD	adbook/init.install /usr/local/bin
ADD	adbook/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

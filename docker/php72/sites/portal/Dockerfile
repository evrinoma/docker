FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 portal" \
	image="php72.portal" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2018-12-06"

COPY	install/portal.sql.tar.gz /root/
COPY	install/portal.tar.gz /root/
ADD	portal/init.install /usr/local/bin
ADD	portal/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

COPY	portal.conf /etc/httpd/conf.d/vhost.conf

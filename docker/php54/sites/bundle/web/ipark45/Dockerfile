FROM \
	evrinoma/php54:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.4 ipark45" \
	image="php54.ipark45" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-18"

COPY	install/ipark45.sql.tar.gz /root/
COPY	install/ipark45.tar.gz /root/
ADD	ipark45/init.install /usr/local/bin
ADD	ipark45/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

COPY	ipark45.conf /etc/httpd/conf.d/vhost.conf

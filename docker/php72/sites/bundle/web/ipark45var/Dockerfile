FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 ipark45var" \
	image="php72.ipark45var" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/ipark45var.sql.tar.gz /root/
COPY	install/ipark45var.tar.gz /root/
ADD	ipark45var/init.install /usr/local/bin
ADD	ipark45var/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	ipark45var.conf /etc/httpd/conf.d/vhost.conf

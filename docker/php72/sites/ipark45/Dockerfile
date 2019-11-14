FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 ipark45" \
	image="php72.ipark45" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-14"

COPY	install/ipark45.sql.tar.gz /tmp/
COPY	install/ipark45.tar.gz /tmp/
ADD	ipark45/init.install /usr/local/bin
ADD	ipark45/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql

#RUN	mkdir -p /opt/WWW/container.ite-ng.ru/data/owncloud && \
#	chmod 770 /opt/WWW/container.ite-ng.ru/data/owncloud/ && \
#	chown -R apache.apache /opt/WWW/container.ite-ng.ru/data/owncloud/

COPY	ipark45.conf /etc/httpd/conf.d/vhost.conf

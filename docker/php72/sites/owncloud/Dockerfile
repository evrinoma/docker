FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 owncloud" \
	image="php72.cloud" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-01"

ADD	owncloud/0001-we-have-done-sftp-automounting-and-disable-local-sto.patch /opt/
ADD	owncloud/init.install /usr/local/bin
ADD	owncloud/init.sql /usr/local/bin
ADD	owncloud/init.ldap.sql /usr/local/bin
ADD	owncloud/init.sftp.sql /usr/local/bin
ADD	owncloud/init.conf /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql && \
	chmod +x /usr/local/bin/init.ldap.sql && \
	chmod +x /usr/local/bin/init.sftp.sql && \
	chmod +x /usr/local/bin/init.conf

RUN	mkdir -p /opt/WWW/container.ite-ng.ru/data/owncloud && \
	chmod 770 /opt/WWW/container.ite-ng.ru/data/owncloud/ && \
	chown -R apache.apache /opt/WWW/container.ite-ng.ru/data/owncloud/

ADD	init.container.part /usr/local/bin
RUN	cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part

COPY	owncloud.conf /etc/httpd/conf.d/vhost.conf

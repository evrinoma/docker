FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 owncloud" \
	image="php74.cloud" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-04-07"

COPY	0001-we-have-done-sftp-automounting-and-disable-local-sto.patch /opt/
COPY	owncloud/init.install /usr/local/bin
COPY	owncloud/init.sql /usr/local/bin
COPY	owncloud/init.ldap.sql /usr/local/bin
COPY	owncloud/init.sftp.sql /usr/local/bin
COPY	owncloud/init.conf /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	init.container.part /usr/local/bin
COPY	create.struct /usr/local/bin
RUN	chmod +x /usr/local/bin/create.struct && \
    cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part

COPY	certs /etc/pki/httpd/certs
COPY	init.openssl /usr/local/bin/init.openssl
RUN	chmod +x /usr/local/bin/init.openssl

RUN	sed -i '1s/^/\n/' /etc/supervisord.d/supervisor.conf && \
	sed -i '1s/^/command=init.openssl\n/' /etc/supervisord.d/supervisor.conf && \
	sed -i '1s/^/[program:init.openssl]\n/' /etc/supervisord.d/supervisor.conf

RUN	yum -y install mod_ssl && \
	mv /etc/httpd/conf.d/ssl.conf{,.bak} && \
	sed -n '1,/<Virtual/ p' /etc/httpd/conf.d/ssl.conf.bak >> /etc/httpd/conf.d/ssl.conf && sed -i '$ d' /etc/httpd/conf.d/ssl.conf && \
	sed -n '/<\/Virtual/,$ p' /etc/httpd/conf.d/ssl.conf.bak > /etc/httpd/conf.d/ssl.conf.end && sed -i '1d' /etc/httpd/conf.d/ssl.conf.end && \
	cat /etc/httpd/conf.d/ssl.conf.end >> /etc/httpd/conf.d/ssl.conf && rm -f /etc/httpd/conf.d/ssl.conf.end && \
	mv -v /etc/httpd/conf.d/welcome.conf{,.bak} && \
	echo  -e "<?php\\n header('Location: https://'.\$_SERVER['HTTP_HOST'].'/');\\n "  > /var/www/html/index.php

EXPOSE 443

FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 owncloud" \
	image="php72.cloud" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-03"

ADD	0001-we-have-done-sftp-automounting-and-disable-local-sto.patch /opt/
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

ADD	init.container.part /usr/local/bin
ADD	create.struct /usr/local/bin
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

FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 roundcubemail" \
	image="php72.roundcubemail" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-09-17"

RUN	yum -y install mod_ssl && \
	mv /etc/httpd/conf.d/ssl.conf{,.bak} && \
	sed -n '1,/<Virtual/ p' /etc/httpd/conf.d/ssl.conf.bak >> /etc/httpd/conf.d/ssl.conf && sed -i '$ d' /etc/httpd/conf.d/ssl.conf && \
	sed -n '/<\/Virtual/,$ p' /etc/httpd/conf.d/ssl.conf.bak > /etc/httpd/conf.d/ssl.conf.end && sed -i '1d' /etc/httpd/conf.d/ssl.conf.end && \
	cat /etc/httpd/conf.d/ssl.conf.end >> /etc/httpd/conf.d/ssl.conf && rm -f /etc/httpd/conf.d/ssl.conf.end && \
	mv -v /etc/httpd/conf.d/welcome.conf{,.bak} && \
	echo  -e "<?php\\n header('Location: https://'.\$_SERVER['HTTP_HOST'].'/');\\n "  > /var/www/html/index.php

COPY	roundcubemail.conf /etc/httpd/conf.d/vhost.conf
COPY	ite-ng.ru /opt/WWW/ite-ng.ru

ADD	init.roundcubemail /usr/local/bin
RUN	chmod +x /usr/local/bin/init.roundcubemail

ADD	init.install /usr/local/bin
ADD	init.sql /usr/local/bin
ADD	init.conf /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql && \
	chmod +x /usr/local/bin/init.conf

RUN	ln -s /opt/WWW/container.ite-ng.ru/conf/mail/dovecot/servers/email/certs/ca.key /etc/pki/CA/certs/ && \
	ln -s /opt/WWW/container.ite-ng.ru/conf/mail/dovecot/servers/email/certs/ca.pem /etc/pki/CA/certs/

#RUN	chmod -R 640 /tmp && \
#	chmod -R 640 /var/tmp

EXPOSE 443
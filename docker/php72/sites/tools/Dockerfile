FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 tools" \
	image="php72.tools" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-22"

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_10.x | bash -  && \
	yum install -y nodejs yarn

ADD	tools/init.install /usr/local/bin
ADD	tools/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

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

RUN	ln -s /opt/WWW/container.ite-ng.ru/conf/php72/sites/tools/certs/ca.key /etc/pki/CA/certs/ && \
	ln -s /opt/WWW/container.ite-ng.ru/conf/php72/sites/tools/certs/ca.pem /etc/pki/CA/certs/

ADD	init.container.part /usr/local/bin
RUN	cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part

COPY	tools.conf /etc/httpd/conf.d/vhost.conf

RUN	touch /var/spool/cron/apache && chown root.root /var/spool/cron/apache  && chmod 400 /var/spool/cron/apache
RUN	chmod -R 640 /tmp && \
	chmod -R 640 /var/tmp

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

EXPOSE 443
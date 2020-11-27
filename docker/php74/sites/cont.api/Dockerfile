FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 cont.api" \
	image="php74.supply" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-11-27"

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_10.x | bash -  && \
	yum install -y nodejs yarn

ADD	cont.api/init.install /usr/local/bin
ADD	cont.api/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install

COPY	cont.api.conf /etc/httpd/conf.d/vhost.conf

RUN	touch /var/spool/cron/apache && chown root.root /var/spool/cron/apache  && chmod 400 /var/spool/cron/apache
RUN	chmod -R 640 /tmp && \
	chmod -R 640 /var/tmp

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

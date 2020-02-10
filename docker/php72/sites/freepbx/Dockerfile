FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 freepbx" \
	image="php72.tools" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-10"

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_8.x | bash -  && \
	yum install -y nodejs yarn

COPY	freepbx.conf /etc/httpd/conf.d/vhost.conf

RUN	chmod -R 640 /tmp && \
	chmod -R 640 /var/tmp

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

EXPOSE 443
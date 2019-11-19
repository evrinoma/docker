FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 tools" \
	image="php72.tools" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-26"

COPY	tools.conf /etc/httpd/conf.d/vhost.conf
RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_8.x | bash -  && \
	yum install -y nodejs yarn

ADD	tools/init.install /usr/local/bin
ADD	tools/init.sql /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql


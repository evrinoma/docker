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
#	rm /etc/yum.repos.d/nodesource*  && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_8.x | bash -  && \
	yum install -y nodejs yarn

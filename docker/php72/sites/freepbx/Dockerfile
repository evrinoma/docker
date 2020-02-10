FROM \
	evrinoma/php72.asterisk13:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 freepbx" \
	image="php72.freepbx" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-10"

COPY	freepbx.conf /etc/httpd/conf.d/vhost.conf
ADD	init.container.part /usr/local/bin
RUN cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part

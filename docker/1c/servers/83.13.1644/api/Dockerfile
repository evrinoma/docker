FROM \
	evrinoma/1c.83.13.1644:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.13.1644.api" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-17"

#1c configs
COPY	deploy /etc/httpd/deploy

COPY	public /usr/local/bin
RUN	chmod +x /usr/local/bin/public && /usr/local/bin/public
RUN	chown -R apache.apache /opt/WWW/1c.ite-ng.ru

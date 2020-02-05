FROM \
	evrinoma/1c.83.15.1700:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.16.1148.api" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-26"

#1c configs
COPY	deploy /etc/httpd/deploy

COPY	public /usr/local/bin
COPY	restartHttpd /usr/local/bin
RUN	chmod +x /usr/local/bin/public && \
	chmod +x /usr/local/bin/restartHttpd && \
	public

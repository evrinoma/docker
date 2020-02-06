FROM \
	evrinoma/1c.83.16.1148:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.16.1148.api" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-06"

COPY	install/params /etc/sysconfig/params

#1c configs
COPY	init.container.part /usr/local/bin
RUN	cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part && \
	sed -i '/###PARAM_INSERT/r /etc/sysconfig/params' /usr/local/bin/public

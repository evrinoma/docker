FROM \
	evrinoma/1c.83.17.1549:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.17.1549:.api" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-01-04"

COPY	install/params /etc/sysconfig/params

#1c configs
COPY	init.container.part /usr/local/bin
RUN	cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part && \
	sed -i '/###PARAM_INSERT/r /etc/sysconfig/params' /usr/local/bin/public

FROM \
	evrinoma/1c.83.13.1644:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.13.1644.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-17"

ADD	srv1cv83 /usr/local/bin/
RUN	chmod +x /usr/local/bin/srv1cv83

RUN	chkconfig --del srv1cv83

RUN	echo "" >> /etc/supervisord.d/supervisor.conf && \
	echo "[program:1C_Enterprise83-server]" >> /etc/supervisord.d/supervisor.conf && \
	echo "command=/usr/local/bin/srv1cv83" >> /etc/supervisord.d/supervisor.conf

ADD	init.1c /usr/local/bin
RUN	chmod +x /usr/local/bin/init.1c

ADD	fetch.1c /usr/local/bin
RUN	chmod +x /usr/local/bin/fetch.1c

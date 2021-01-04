FROM \
	evrinoma/1c.83.17.1549:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.17.1549.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-01-04"

ADD	srv1cv83 /usr/local/bin/
RUN	chmod +x /usr/local/bin/srv1cv83

RUN	chkconfig --del srv1cv83

RUN	echo "" >> /etc/supervisord.d/supervisor.conf && \
	echo "[program:1C_Enterprise83-server]" >> /etc/supervisord.d/supervisor.conf && \
	echo "command=/usr/local/bin/srv1cv83" >> /etc/supervisord.d/supervisor.conf

ADD	init.1c /usr/local/bin
RUN	chmod +x /usr/local/bin/init.1c

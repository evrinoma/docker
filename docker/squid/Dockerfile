FROM \
	evrinoma/samba:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma squid" \
	image="squid" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-16"

#SQUID
RUN	yum install squid -y

RUN	rm -f /etc/squid/*.default
COPY	supervisor.squid.conf /etc/supervisord.d/squid.conf

COPY	init.squid /usr/local/bin/init.squid
COPY	devel.squid /usr/local/bin/devel.squid
COPY	stop.squid /usr/local/bin/stop.squid
COPY	start.devel /usr/local/bin/start.devel
COPY	stop.devel /usr/local/bin/stop.devel

RUN	chmod +x /usr/local/bin/init.squid && \
	chmod +x  /usr/local/bin/devel.squid && \
	chmod +x  /usr/local/bin/stop.squid && \
	chmod +x  /usr/local/bin/start.devel && \
	chmod +x  /usr/local/bin/stop.devel

EXPOSE 3128

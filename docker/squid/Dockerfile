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

COPY	squid.logrotate.conf /etc/logrotate.d/squid
RUN	yum install logrotate -y

RUN	yum install cronie -y && \
	sed -i -e '/pam_loginuid.so/s/^/#/' /etc/pam.d/crond && \
	echo "#*  *  *  *  * /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/cont/bin/console  ###  > /dev/null 2>&1" >> /etc/crontab.root && \
	echo "0 0  *  *  * logrotate -f /etc/logrotate.d/squid > /dev/null 2>&1" >> /etc/crontab.root  && \
	crontab -u root /etc/crontab.root && \
	crontab -u root -l
COPY    reconfig /usr/local/bin/
COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container && \
	chmod 700 /usr/local/bin/reconfig

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

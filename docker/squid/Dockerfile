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

RUN	echo "" >> /etc/supervisord.d/supervisor.conf && \
	echo "[program:squid]" >> /etc/supervisord.d/supervisor.conf && \
	echo "command=service squid start" >> /etc/supervisord.d/supervisor.conf

EXPOSE 3128

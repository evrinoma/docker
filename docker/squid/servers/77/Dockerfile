FROM \
	evrinoma/squid:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma squid 77" \
	image="squid" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-02-03"

ARG	netbios_name=''
ARG	server_string=''
ARG	server_IP=''
ARG	server_domain=''
ARG	cache_time=''

RUN	echo "netbios_name=${netbios_name}" > /etc/sysconfig/vhost && \
	echo "server_string=${server_string}" >> /etc/sysconfig/vhost && \
	echo "server_IP=${server_IP}" >> /etc/sysconfig/vhost && \
	echo "server_domain=${server_domain}" >> /etc/sysconfig/vhost && \
	echo "cache_time=${cache_time}" >> /etc/sysconfig/vhost

RUN	sed -i -e "s@netbios_name@${netbios_name}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@server_string@${server_string}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@server_IP@${server_IP}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@server_domain@${server_domain}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@cache_time@${cache_time}@g" /etc/samba/smb.conf

COPY	/squid /etc/squid/

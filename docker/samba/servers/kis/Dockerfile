FROM \
	evrinoma/samba:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma samba kis" \
	image="samba kis" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-06-13"

ARG	netbios_name=''
ARG	server_string=''
ARG	server_IP=''
ARG	server_domain=''

RUN	echo "netbios_name=${netbios_name}" > /etc/sysconfig/vhost && \
	echo "server_string=${server_string}" >> /etc/sysconfig/vhost && \
	echo "server_domain=${server_domain}" >> /etc/sysconfig/vhost && \
	echo "server_IP=${server_IP}" >> /etc/sysconfig/vhost

RUN	sed -i -e "s@netbios_name@${netbios_name}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@server_string@${server_string}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@server_domain@${server_domain}@g" /etc/samba/smb.conf
RUN	sed -i -e "s@server_IP@${server_IP}@g" /etc/samba/smb.conf

COPY	install/samba.share.definitions /etc/sysconfig/samba.share.definitions
RUN	sed -i '/###SAMBA_SHARE_DEFINITIONS/r /etc/sysconfig/samba.share.definitions' /etc/samba/smb.conf

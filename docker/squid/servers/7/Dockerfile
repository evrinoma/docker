FROM \
	evrinoma/squid:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma squid 7" \
	image="squid" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-16"

RUN	mv -v /etc/samba/smb.conf{,.bak}
COPY	smb.conf /etc/samba/smb.conf

#RUN	mv -v /etc/sysconfig/network{,.bak} && \
#	cat /etc/sysconfig/network.bak | sed 's/^=*HOSTNAME=localhost.localdomain*$/HOSTNAME=DOCK_SQUID_7/' > /etc/sysconfig/network;

#RUN	echo 'DOCK_SQUID_7' > /etc/hostname;

COPY	/etc /etc/

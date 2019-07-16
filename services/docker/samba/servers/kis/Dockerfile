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

RUN	mv -v /etc/samba/smb.conf{,.bak}
COPY	smb.conf /etc/samba/smb.conf

RUN	mv -v /etc/sysconfig/network{,.bak} && \
	cat /etc/sysconfig/network.bak | sed 's/^=*HOSTNAME=localhost.localdomain*$/HOSTNAME=DOCK_SMB_KIS/' > /etc/sysconfig/network;

RUN	echo 'DOCK_SMB_KIS' > /etc/hostname;

#RUN	/etc/init.d/winbind start && /etc/init.d/nmb start && /etc/init.d/smb start

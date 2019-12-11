FROM \
	evrinoma/squid:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma squid 77" \
	image="squid" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-16"

RUN	mv -v /etc/samba/smb.conf{,.bak}
COPY	smb.conf /etc/samba/smb.conf

COPY	/squid /etc/squid/

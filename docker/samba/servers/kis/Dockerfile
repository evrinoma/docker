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

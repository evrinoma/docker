FROM \
	evrinoma/exim:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma exim Image" \
	image="exim.mail" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-22"

COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

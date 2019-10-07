FROM \
	evrinoma/bind:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma bind Image" \
	image="mail bind" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-07"

RUN	mv -v /etc/named.conf{,.bak} && \
	mv -v /etc/named{.bak} && \
	chown -R named.named /var/named

COPY	named.conf /etc
COPY	named.external.zones /etc
COPY	named/ /etc


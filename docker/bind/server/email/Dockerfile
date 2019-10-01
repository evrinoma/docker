FROM \
	evrinoma\bind:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma bind Image" \
	image="bind" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-01"

RUN	mv -v /etc/resolv.conf{,.bak} && \
	mv -v /etc/named.conf{,.bak} && \
	echo "nameserver 172.16.45.4" > /etc/resolv.conf

COPY	named.conf /etc
COPY	named.external.zones /etc
COPY	named.internal.zones /etc

RUN	mkdir -p /var/named/internal && \
	mkdir -p /var/named/external
	chown -R named.named /var/named
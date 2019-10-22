FROM \
	evrinoma/exim:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma exim Image" \
	image="exim.mail" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-22"

RUN	mkdir -p  /var/log/clamav/ && \
	touch  /var/log/clamav/freshclam.log && \
	touch  /var/log/clamav/clamd.log && \
	chown -R  clamav.clamav /var/log/clamav/ && \
	chmod -R 700 /var/log/clamav

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf

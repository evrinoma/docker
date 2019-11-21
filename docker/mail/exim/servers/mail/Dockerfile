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

RUN	mkdir -p /opt/WWW/container.ite-ng.ru/logs/exim && \
	cp -r /opt/exim/tmp/ /opt/WWW/container.ite-ng.ru/logs/exim && \
	mkdir -p /opt/WWW/container.ite-ng.ru/logs/exim/var && \
	cp -r /opt/exim/var/log/ /opt/WWW/container.ite-ng.ru/logs/exim/var/ && \
	rm -rf /opt/exim/var/log/ && \
	rm -rf /opt/exim/tmp/ && \
	ln -s /opt/WWW/container.ite-ng.ru/logs/exim/var/log/ /opt/exim/var/ && \
	ln -s /opt/WWW/container.ite-ng.ru/logs/exim/tmp/ /opt/exim/

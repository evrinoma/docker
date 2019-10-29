FROM \
	evrinoma/php72.cont.ite-ng.ru:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 cont" \
	image="php72.cont" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2018-04-18"

COPY	hide /usr/local/bin/
RUN	head /dev/urandom | tr -dc A-Za-z0-9 | head -c 10  | passwd --stdin root && \
	chmod 700 /usr/local/bin/hide && \
	hide

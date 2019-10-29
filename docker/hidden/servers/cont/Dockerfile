FROM \
	evrinoma/php72.cont:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 cont" \
	image="php72.cont" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2018-04-18"

COPY	hide /usr/local/bin/
RUN	chmod 700 hide /usr/local/bin/hide && \
	hide

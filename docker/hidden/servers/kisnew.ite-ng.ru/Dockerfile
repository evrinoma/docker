FROM \
	evrinoma/php53.new.ite-ng.ru:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.3 kisnew.ite-ng.ru" \
	image="php53.new.ite-ng.ru" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2018-10-29"

COPY	hide /usr/local/bin/
RUN	chmod 700 /usr/local/bin/hide && \
	hide
#RUN	cat /etc/supervisord.d/supervisor.conf >> /etc/supervisord.conf
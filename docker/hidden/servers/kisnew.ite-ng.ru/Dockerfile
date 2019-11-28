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

RUN	mv -v /usr/local/bin/composer{,.bak}
COPY	cd /usr/local/bin/
COPY	composer /usr/local/bin/
COPY	hide /usr/local/bin/
RUN	chmod 700 /usr/local/bin/cd && \
	chmod 700 /usr/local/bin/composer && \
	chmod 700 /usr/local/bin/hide && \
	head /dev/urandom | tr -dc A-Za-z0-9 | head -c 10  | passwd --stdin root && \
	sed -i -e "s/#\!\/bin\/bash/#\!\/bin\/pribash/" /usr/local/bin/init.container && \
	hide

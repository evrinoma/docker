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

RUN	mv -v /usr/local/bin/composer{,.bak}
COPY	cd /usr/local/bin/
COPY	composer /usr/local/bin/
COPY	hide /usr/local/bin/
RUN	chmod 700 /usr/local/bin/cd && \
	chmod 700 /usr/local/bin/composer && \
	chmod 700 /usr/local/bin/hide && \
	head /dev/urandom | tr -dc A-Za-z0-9 | head -c 10  | passwd --stdin root && \
	chown -R apache.apache /var/www/ && \
	sed -i -e "s/#\!\/bin\/bash/#\!\/bin\/pribash/" /usr/local/bin/init.container && \
	hide

#COPY	perm /usr/local/bin/
#RUN	chmod 700 /usr/local/bin/perm


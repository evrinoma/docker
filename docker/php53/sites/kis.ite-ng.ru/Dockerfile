FROM \
	evrinoma/php53:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.3 kis.ite-ng.ru" \
	image="php-5.3" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-20"

RUN	ln -s /opt/WWW/container.ite-ng.ru/documents/docs/ /opt/WWW/container.ite-ng.ru/kis_content/docs && \
	ln -s /opt/WWW/container.ite-ng.ru/documents/1c/ /opt/WWW/container.ite-ng.ru/kis_content/1c && \
	ln -s /opt/WWW/container.ite-ng.ru/documents/excel/ /opt/WWW/container.ite-ng.ru/kis_content/excel && \
	ln -s /opt/WWW/container.ite-ng.ru/documents/tmp/ /opt/WWW/container.ite-ng.ru/kis_content/tmp

COPY	kis.ite-ng.ru.conf /etc/httpd/conf.d/vhost.conf

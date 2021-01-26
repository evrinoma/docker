FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma MatterMost Image" \
	image="mattermost" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-01-25"

ONBUILD	ARG	VERSION=''
ONBUILD ARG	VHOST=''
ONBUILD ARG	database_host=''
ONBUILD ARG	database_port=''
ONBUILD ARG	database_name=''
ONBUILD ARG	database_user=''
ONBUILD ARG	database_password=''

RUN	dnf -y install mc wget httpd  iproute nginx mod_ssl

RUN	dnf -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
	dnf -y install https://rpms.remirepo.net/enterprise/remi-release-8.rpm

RUN	dnf -y install gcc \
	make \
	glibc-langpack-en;

RUN	dnf -y install git \
	passwd \
	supervisor \
	openssh-server;

RUN	dnf -y install \
	telnet \
	exim.x86_64 \
	mailx \
	bzip2 \
	unzip \
	patch;

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";

#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	mkdir -p /run/supervisor/ && \
	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf && \
	sed -i -e "s/^\[unix_http_server\]/;\[unix_http_server\]/" /etc/supervisord.conf && \
	sed -i -e "s/^file\=\/run\/supervisor\/supervisor.sock/;&/" /etc/supervisord.conf

##mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

RUN	yum -y install mod_ssl && \
	mv /etc/httpd/conf.d/ssl.conf{,.bak} && \
	sed -n '1,/<Virtual/ p' /etc/httpd/conf.d/ssl.conf.bak >> /etc/httpd/conf.d/ssl.conf && sed -i '$ d' /etc/httpd/conf.d/ssl.conf && \
	sed -n '/<\/Virtual/,$ p' /etc/httpd/conf.d/ssl.conf.bak > /etc/httpd/conf.d/ssl.conf.end && sed -i '1d' /etc/httpd/conf.d/ssl.conf.end && \
	cat /etc/httpd/conf.d/ssl.conf.end >> /etc/httpd/conf.d/ssl.conf && rm -f /etc/httpd/conf.d/ssl.conf.end && \
	mv -v /etc/httpd/conf.d/welcome.conf{,.bak} && \
	echo  -e "<?php\\n header('Location: https://'.\$_SERVER['HTTP_HOST'].'/');\\n "  > /var/www/html/index.php

RUN mkdir -p /etc/pki/nginx/mattermost/

ADD mattermost.nginx.conf /etc/nginx/conf.d
ADD mattermost.httpd.conf /etc/httpd/conf.d/vhost.conf
ADD init.install /usr/local/bin
ADD init.sql /usr/local/bin
RUN chmod +x /usr/local/bin/init.install

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80 443

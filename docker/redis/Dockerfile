FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Redis Image" \
	image="php74" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-10-28"

ONBUILD	ARG	MODE=''

RUN	yum -y install mc wget && \
	yum -y install httpd && \
	yum -y install iproute;

RUN	dnf -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
	dnf -y install https://rpms.remirepo.net/enterprise/remi-release-8.rpm

RUN	yum -y install gcc \
	make \
	glibc-langpack-en;

RUN	yum -y install git \
	passwd \
	supervisor \
	openssh-server;

RUN	yum -y install \
	telnet \
	exim.x86_64 \
	mailx \
	bzip2 \
	unzip \
	patch;

RUN	yum install redis -y

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

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

ADD	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

RUN	yum install cronie -y && \
	sed -i -e '/pam_loginuid.so/s/^/#/' /etc/pam.d/crond && \
	touch /etc/crontab.apache
ADD	reconfig /usr/local/bin/
RUN	chmod +x /usr/local/bin/reconfig

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 6379

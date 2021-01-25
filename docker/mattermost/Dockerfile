FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Matermost Image" \
	image="matermost" \
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

ADD init.install /usr/local/bin
ADD init.sql /usr/local/bin
RUN chmod +x /usr/local/bin/init.install

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 8065

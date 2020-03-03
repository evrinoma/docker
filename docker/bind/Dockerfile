FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma bind Image" \
	image="bind" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-01"

ONBUILD	ARG	MODE=''
ONBUILD	ARG	INSTALL_DIR='/opt/bind'
ONBUILD	ARG	GIT_IP=''

RUN	yum -y install mc wget && \
	yum -y install iproute && \
	yum -y update;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	supervisor \
	openssh-server;

RUN	yum -y install \
	telnet;

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
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

RUN	yum -y install bind bind-utils telnet

RUN	mkdir -p /var/named && \
	mkdir -p /var/log/named && \
	chown -R named.named /var/named && \
	chown -R named.named /var/log/named

COPY	init.container /usr/local/bin/
COPY	restartBind /usr/local/bin/
COPY    init.install /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container && \
    chmod +x /usr/local/bin/restartBind && \
    chmod 755 /usr/local/bin/init.install

COPY	install/bind /etc/sysconfig/bind

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 53 8953

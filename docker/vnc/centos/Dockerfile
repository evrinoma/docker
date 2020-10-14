FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma VNC Image" \
	image="vnc" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-16"

RUN	yum -y install mc wget && \
	yum -y install iproute && \
	yum -y update;

RUN	dnf -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
	dnf -y install https://rpms.remirepo.net/enterprise/remi-release-8.rpm
#RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
#	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	supervisor \
	passwd \
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
RUN	mkdir -p /run/supervisor/ && \
	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf && \
	sed -i -e "s/^\[unix_http_server\]/;\[unix_http_server\]/" /etc/supervisord.conf && \
	sed -i -e "s/^file\=\/run\/supervisor\/supervisor.sock/;&/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

RUN	yum install tigervnc-server -y 
RUN	yum groupinstall xfce -y 

COPY	vnc/ /root/

RUN	export LANG=en_US.UTF-8
#copy terminal settings
COPY xfce4 /root/.config/xfce4
RUN	sed -i '/xfce-polkit/d' /etc/xdg/autostart/xfce-polkit.desktop

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 5901

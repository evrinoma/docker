FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-16"

RUN	echo "nameserver 172.20.1.5" > /etc/resolv.conf && \
	echo "nameserver 8.8.8.8" >> /etc/resolv.conf

RUN	yum -y install mc wget && \
	yum -y install httpd && \
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
	echo 0631 | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

#1c install
COPY	install /opt/WWW/install
WORKDIR	/opt/WWW/install
RUN	rpm -ivh 1C_Enterprise83-common-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-common-nls-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-ws-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-ws-nls-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-server-8.3.13-1644.x86_64.rpm && \
	rm -rf /opt/WWW/install
WORKDIR	/

#1c configs
RUN	touch /etc/httpd/conf.d/1c.conf && mkdir /opt/WWW/1c.ite-ng.ru

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80 1540-1541 1560-1591


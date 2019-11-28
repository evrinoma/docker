FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com
FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Postgre SQL Image" \
	image="postgre.1c" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-25"

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

RUN	export LANG=en_US.UTF-8

RUN	yum -y install libxslt libicu systemd-sysv libicu-devel tcl

RUN	localedef -c -f UTF-8 -i ru_RU ru_RU.UTF-8

ENTRYPOINT ["/usr/bin/supervisord","-n"]

EXPOSE 22 5432

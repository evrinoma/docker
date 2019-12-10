FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Java Image" \
	image="java" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-10"

RUN	yum -y install mc wget && \
	yum -y install iproute;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	openssh-server \
	supervisor;

RUN	yum -y install \
	telnel \
	exim.x86_64 \
	mailx \
	bzip2 \
	unzip \
	patch \
	java;

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
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf
#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

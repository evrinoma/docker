FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma wowza Image" \
	image="wowza" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-23"

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
	telnet \
	patch.x86_64;

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

RUN	yum -y install which

#RUN	touch /etc/profile.d/wowza.sh && \
#	chmod 755 /etc/profile.d/wowza.sh && \
#	echo '#/etc/profile.d wowza set' >> /etc/profile.d/wowza.sh && \
#	echo 'JAVA_HOME=/usr/local/WowzaStreamingEngine/java' >> /etc/profile.d/wowza.sh && \
#	echo 'export PATH=$JAVA_HOME/bin:$PATH' >> /etc/profile.d/wowza.sh

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

COPY	runWowza /usr/local/bin
COPY	fetch.runWowza /usr/local/bin
COPY    init.install /usr/local/bin/
RUN	chmod 755  /usr/local/bin/runWowza && \
	chmod 755  /usr/local/bin/fetch.runWowza && \
	chmod 755 /usr/local/bin/init.install

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 1935 8084-8086 8088
#6970-9999

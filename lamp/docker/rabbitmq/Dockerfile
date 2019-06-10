FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma RabbitMq Image" \
	image="rabbit" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-05-21"

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
	openssh-server \
	supervisor;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";

#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa &&\
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa &&\
	echo 0631 | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

RUN	yum -y install rabbitmq-server

RUN	yum -y install python34-pip && \
	pip3.4 install pika;

RUN	echo 'export HOSTNAME="rabbitmq"' > /etc/profile.d/rabbitmq.sh && \
	echo "nameserver 172.20.1.5" > /etc/resolv.conf

ENTRYPOINT ["/usr/bin/supervisord","-n"]

# 
# expose some ports
#
# 5672 rabbitmq-server - amqp port
# 15672 rabbitmq-server - for management plugin
# 4369 epmd - for clustering
# 25672 rabbitmq-server - for clustering
EXPOSE 5672 15672 4369 25672 22


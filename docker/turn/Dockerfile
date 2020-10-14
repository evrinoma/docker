FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma TURN Image" \
	image="turn" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-10-14"

ONBUILD ARG	EXTERNAL_IP=''
ONBUILD ARG	TURN_PASS=''

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

RUN	dnf install coturn coturn-utils -y
RUN	echo "EXTERNAL_IP=${EXTERNAL_IP}" > /etc/sysconfig/vhost && \
#	echo "HASH=$(openssl rand -hex 32)" >>/etc/sysconfig/vhost
	echo "HASH=${TURN_PASS}" >>/etc/sysconfig/vhost


RUN	echo "cli-password=$(cat /etc/sysconfig/vhost | grep HASH | sed -r 's/.*=(.*)$/\1/')" >>  /etc/coturn/turnserver.conf && \
	echo "use-auth-secret" >>  /etc/coturn/turnserver.conf && \
	echo "static-auth-secret=$(cat /etc/sysconfig/vhost | grep HASH | sed -r 's/.*=(.*)$/\1/')" >>  /etc/coturn/turnserver.conf && \
	echo "user=kurento:kurento" >>  /etc/coturn/turnserver.conf && \
	echo "realm=ite-ng.ru" >>  /etc/coturn/turnserver.conf && \
	if [ ! -z "${EXTERNAL_IP}" ]; then  echo "external-ip=${EXTERNAL_IP}" >> /etc/coturn/turnserver.conf ; fi


ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

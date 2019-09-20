FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma dovecot Image" \
	image="dovecot" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-09-86"

RUN	echo "nameserver 172.20.1.5" > /etc/resolv.conf && \
	echo "nameserver 8.8.8.8" >> /etc/resolv.conf

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
	echo 0631 | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

RUN	yum -y install dovecot dovecot-mysql dovecot-pigeonhole dovecot-devel dovecot-pgsql telnet postfix
COPY	postfix/certs /etc/postfix/certs

RUN	groupadd  -g 2000 vmail && \
	useradd -d /opt/WWW/container.ite-ng.ru/data -g 2000 -u 2000 vmail && \
	mkdir -p -v /var/log/dovecot/ && \
	chown -R vmail.vmail /opt/WWW/container.ite-ng.ru/data && \
#	chown -R vmail.vmail /var/run/dovecot/
	chown -R vmail.vmail /var/log/dovecot/

RUN	yum -y install php72w php72w-mysql php72w-cli php72w-ldap
RUN	systemctl enable dovecot && systemctl enable postfix

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

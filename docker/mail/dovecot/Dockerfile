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

RUN	yum -y install dovecot dovecot-mysql dovecot-pigeonhole dovecot-devel dovecot-pgsql telnet postfix
COPY	postfix/certs /etc/postfix/certs

RUN	groupadd  -g 2000 vmail && \
	useradd -d /opt/WWW/container.ite-ng.ru/data -g 2000 -u 2000 vmail && \
	mkdir -p -v /var/log/dovecot/ && \
	chown -R vmail.vmail /opt/WWW/container.ite-ng.ru/data && \
#	chown -R vmail.vmail /var/run/dovecot/
	chown -R vmail.vmail /var/log/dovecot/

#postfixadmin install
RUN	yum -y install httpd php72w php72w-mysql php72w-cli php72w-ldap php72w-mbstring php72w-imap
RUN	systemctl enable dovecot && systemctl enable postfix

COPY	postfixadmin/postfixadmin.conf /etc/httpd/conf.d/vhost.conf
ADD	postfixadmin/init.install /usr/local/bin
ADD	postfixadmin/init.sql /usr/local/bin
ADD	postfixadmin/init.conf /usr/local/bin
RUN	chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql && \
	chmod +x /usr/local/bin/init.conf

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

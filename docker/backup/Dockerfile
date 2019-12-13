FROM \
		centos:centos7

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma BackUp Image" \
		image="backup" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2019-12-13"

#ENV	container docker

#RUN	(cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done); \
#	rm -f /lib/systemd/system/multi-user.target.wants/*;\
#	rm -f /etc/systemd/system/*.wants/*;\
#	rm -f /lib/systemd/system/local-fs.target.wants/*; \
#	rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
#	rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
#	rm -f /lib/systemd/system/basic.target.wants/*;\
#	rm -f /lib/systemd/system/anaconda.target.wants/*;
#	VOLUME [ "/sys/fs/cgroup" ]
#CMD	["/usr/sbin/init"]

RUN	yum -y install mc && \
	yum -y install git && \
	yum -y install wget;

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

RUN	yum -y install mariadb-server mariadb && \
	yum -y install php php-mysql php-gd && \
	yum clean all;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

COPY	init.container /usr/local/bin/
COPY	reconfig /usr/local/bin/
COPY	backupexec /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container && \
	chmod 700 /usr/local/bin/reconfig && \
	chmod 700 /usr/local/bin/backupexec

RUN	yum install cronie -y && \
	sed -i -e '/pam_loginuid.so/s/^/#/' /etc/pam.d/crond && \
	echo "#*  *  *  *  * /usr/bin/php /opt/WWW/container.ite-ng.ru/projects/cont/bin/console  ###  > /dev/null 2>&1" >> /etc/crontab.apache && \
	crontab -u apache /etc/crontab.apache && \
	crontab -u apache -l

RUN	yum -y install jq.x86_64

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

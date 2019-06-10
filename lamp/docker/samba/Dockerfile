FROM \
	centos:centos6

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma SAMBA Image" \
	image="samba" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-06-10"

RUN	echo "nameserver 172.20.1.5" > /etc/resolv.conf && \
	echo "nameserver 8.8.8.8" >> /etc/resolv.conf

RUN	yum -y install mc wget && \
	yum -y install httpd && \
	yum -y install iproute && \
	yum -y update;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-6.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el6/latest.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	openssh-server;

RUN	yum -y install \
	telnel \
	pcre \
	exim.x86_64 \
	mailx;

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
RUN	mv -v /etc/supervisord.conf{,.bak}
COPY	supervisord.conf /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/
#httpd hosts
COPY	vhost.conf /etc/httpd/conf.d/vhost.conf
#mail
RUN	mv -v /etc/exim/exim.conf{,.bak} && \
	mv -v /usr/sbin/sendmail{,.bak} && \
	cp /usr/sbin/exim /usr/sbin/sendmail && \
	chmod 4755 /usr/sbin/sendmail && \
	mv /bin/mail{,.bak} && \
	ln -s /bin/mailx /bin/mail
#clean
RUN	echo "nameserver 172.20.1.5" > /etc/resolv.conf && \

COPY	exim.conf /etc/exim/exim.conf


RUN	yum -y install \
	samba \
	samba-client \
	samba-common;

RUN	chkconfig smb on && \
	service smb start && \
	smbstatus


ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80


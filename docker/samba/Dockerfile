FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma SAMBA Image" \
	image="samba" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-06-10"

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
	echo 1234 | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

#mail
RUN	mv -v /etc/exim/exim.conf{,.bak} && \
	mv -v /usr/sbin/sendmail{,.bak} && \
	cp /usr/sbin/exim /usr/sbin/sendmail && \
	chmod 4755 /usr/sbin/sendmail && \
	mv /bin/mail{,.bak} && \
	ln -s /bin/mailx /bin/mail
#clean
COPY	exim.conf /etc/exim/exim.conf

#ntp
RUN	yum -y install ntp && \
	mv -v /etc/ntp.conf{,.bak} && \
	cat /etc/ntp.conf.bak | sed 's;\\(.*\\)server;\\1#server;' > /etc/ntp.conf && \
	echo "server 172.20.1.6" >> /etc/ntp.conf
#	chkconfig ntpd --level 35 on && \
#	service ntpd start;

RUN	yum -y install \
	samba \
	samba-client \
	samba-common \
	samba-winbind \
	samba-winbind-clients \
	system-config-samba \
	system-config-samba-docs;

RUN	mv -v /etc/krb5.conf{,.bak}
COPY	krb5.conf /etc/krb5.conf

RUN	cp /etc/nsswitch.conf{,.bak} && \
	sed -i -e "s/passwd:     files/passwd:     files winbind/g" /etc/nsswitch.conf && \
	sed -i -e "s/group:      files/group:      files winbind/g" /etc/nsswitch.conf

#RUN	touch /etc/profile.d/samba.sh && \
#	chmod 755 /etc/profile.d/samba.sh && \
#	echo '#/etc/profile.d Samba set' >> /etc/profile.d/samba.sh && \
#	echo 'KRB5CCNAME=FILE:/run/samba/krb5cc_samba' >> /etc/profile.d/samba.sh && \
#	echo 'export $KRB5CCNAME' >> /etc/profile.d/samba.sh

COPY	init.samba /usr/local/bin/init.samba
COPY	install/samba.credentials /etc/sysconfig/samba.credentials
RUN	chmod +x /usr/local/bin/init.samba
RUN	sed -i '/###SAMBA_INSERT/r /etc/sysconfig/samba.credentials' /usr/local/bin/init.samba

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 139 445 137 138


FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma exim Image" \
	image="exim" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-07"

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
	telnet \
	patch.x86_64;

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


#exim
RUN	yum -y install sqlite-devel.i686 sqlite-devel.x86_64 \
	pcre.i686 pcre.x86_64 \
	pcre-devel.i686 pcre-devel.x86_64 \
	libXt-devel.i686 libXt-devel.x86_64 \
	libXaw-devel.i686 libXaw-devel.x86_64 \
	firebird-devel.i686 firebird-devel.x86_64 \
	mariadb-devel.i686 mariadb-devel.x86_64 mariadb.x86_64 \
	libdb-devel.x86_64 libdb-devel.i686 libdb.x86_64 \
	openssl-devel.x86_64 openssl.x86_64 openssl-libs.x86_64 \
	tcltls.x86_64 tcltls-devel.x86_64

RUN	adduser --user-group --system --no-create-home --shell /bin/false exim && \
	usermod -L exim

WORKDIR /opt
RUN	git clone https://github.com/Exim/exim.git
#exim
RUN	mkdir -p /opt/exim/tmp /opt/exim/var/spool/exim
WORKDIR /opt/exim/src
RUN	mysql_config --help
COPY	0001-initial-local.patch src
COPY	0002-fix-local.patch src
COPY	0003-fix-version.patch src
RUN	patch -Np1 -i src/0001-initial-local.patch  && \
	patch -Np1 -i src/0002-fix-local.patch  && \
	patch -Np1 -i src/0003-fix-version.patch
RUN	make
COPY	exim.conf /opt/exim/etc
RUN	chown exim:exim -R /opt/exim/
	
WORKDIR /

#spamassassin
RUN	yum install spamassassin.x86_64 -y && \
	mv -v /etc/sysconfig/spamassassin{,.bak} && \
	ls -la /etc/sysconfig/ && \
	echo 'SPAMDOPTIONS="-d -c -m5 -H -s /opt/exim/tmp/spamd.log"' > /etc/sysconfig/spamassassin && \
	systemctl enable spamassassin

RUN	adduser --user-group --system --no-create-home --shell /bin/false clamav && \
	usermod -L clamav

#build clamav
RUN	yum -y install gcc gcc-c++ libxml2 json-c-devel libxml2-devel bzip2 file libcurl libcurl-devel bzip2-devel
RUN	mkdir /opt/clamav && \
	git clone https://github.com/Cisco-Talos/clamav-devel.git /opt/clamav/src

WORKDIR /opt/clamav/src
RUN	./configure --prefix=/opt/clamav/ --disable-clamonacc && \
	make && \
	make install
RUN	mkdir -p /opt/clamav/etc/ && \
	mkdir -p /var/run/clamd.scan/ && chown -R clamav.clamav /var/run/clamd.scan && \
	mkdir -p /var/lib/clamav/tmp && chown -R clamav.clamav /var/lib/clamav

COPY	clamav-daemon.service /usr/lib/systemd/system
COPY	clamav-freshclam.service /usr/lib/systemd/system
COPY	clamd@scan.service /usr/lib/systemd/system
COPY	clamav-daemon.socket /usr/lib/systemd/system
COPY	clamd@freshclam.service /usr/lib/systemd/system
COPY	clamd@.service /usr/lib/systemd/system
COPY	freshclam.conf /opt/clamav/etc/
COPY	scan.conf /opt/clamav/etc/

#	/opt/clamav/bin/freshclam -d -c 4 && \
#	chown -R clamav.clamav /var/run/clamd.scan && chown -R clamav.clamav /var/lib/clamav &&\
RUN	systemctl enable clamd@freshclam.service && \
	systemctl enable clamd@scan.service


WORKDIR /

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

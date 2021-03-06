FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma exim Image" \
	image="exim" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-04-13"

RUN	dnf -y install mc wget iproute &&\
	dnf -y update;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
	rpm -Uvh https://rpms.remirepo.net/enterprise/remi-release-8.rpm;

RUN	dnf -y install gcc gcc-c++ make;

RUN	dnf -y install git supervisor passwd openssh-server;

RUN	dnf -y install telnet patch.x86_64;


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
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

#exim
RUN	dnf -y install sqlite-devel.i686 sqlite-devel.x86_64 \
	pcre.i686 pcre.x86_64 \
	pcre-devel.i686 pcre-devel.x86_64 \
	libXt-devel.i686 libXt-devel.x86_64 \
	libXaw-devel.i686 libXaw-devel.x86_64 \
	firebird-devel.x86_64 \
	mariadb-devel.x86_64 mariadb.x86_64 \
	libdb-devel.x86_64 libdb-devel.i686 libdb.x86_64 \
	openssl-devel.x86_64 openssl.x86_64 openssl-libs.x86_64 \
	tcltls.x86_64 tcltls-devel.x86_64

RUN	adduser --user-group --system --no-create-home --shell /bin/false exim && \
	usermod -L exim && mkdir /home/exim && chown -R exim.exim /home/exim

#exim
RUN	mkdir -p /opt/exim/tmp /opt/exim/var/spool/exim /opt/exim/etc /opt/exim/build
WORKDIR /opt/exim/build 
RUN	git clone https://github.com/Exim/exim.git .
WORKDIR /opt/exim/build/src
RUN	mysql_config --help
COPY	0001-initial-local.patch src
COPY	0002-fix-local.patch src
COPY	0003-fix-version.patch src
RUN	patch -Np1 -i src/0001-initial-local.patch  && \
	patch -Np1 -i src/0002-fix-local.patch  && \
	patch -Np1 -i src/0003-fix-version.patch
RUN	make && make install
RUN	mkdir -p /opt/exim/var/log && mkdir -p /opt/exim/tmp
RUN	chown exim:exim -R /opt/exim/ && \
	echo 'DAEMON=yes' >> /etc/sysconfig/exim && \
	echo 'QUEUE=1h' >> /etc/sysconfig/exim
COPY	exim.conf /opt/exim/etc
RUN	setcap 'cap_net_bind_service=+ep' /opt/exim/bin/$(ls -la /opt/exim/bin |grep ^l | awk '{print $11}')
WORKDIR /

#spamassassin
RUN	dnf install spamassassin.x86_64 portreserve -y && \
	mv -v /etc/sysconfig/spamassassin{,.bak} && \
	ls -la /etc/sysconfig/ && \
	echo 'SPAMDOPTIONS="-d -c -m5 -H -s /opt/exim/tmp/spamd.log"' > /etc/sysconfig/spamassassin && \
	systemctl enable spamassassin && \
	mv -v /etc/mail/spamassassin/	/etc/mail/spamassassin.bak && \
	mkdir -p /home/nobody/.spamassassin /home/nobody/.razor && \
	chown -R nobody:nobody /home/nobody && \
	chmod -R 755 /home/nobody && \
	chmod -R 777 /home/nobody/.spamassassin && \
	chmod -R 777 /home/nobody/.razor && \
	ln -s /home/nobody/.razor/ /root/.razor && \
	ln -s /home/nobody/.spamassassin/ /root/.spamassassin && \
	usermod --home /home/nobody/ nobody && \
	touch /etc/profile.d/exim.sh && \
	chmod 755 /etc/profile.d/exim.sh && \
	echo '#/etc/profile.d Exim set' >> /etc/profile.d/exim.sh && \
	echo 'EXIM_HOME=/opt/exim' >> /etc/profile.d/exim.sh && \
	echo 'export PATH=$EXIM_HOME/bin:$PATH' >> /etc/profile.d/exim.sh
COPY	spamassassin/ /etc/mail/spamassassin/

RUN	mkdir -p /opt/dcc/src && \
	wget  -P /opt/dcc/src/ http://www.rhyolite.com/dcc/source/dcc.tar.Z && \
	tar -zxvf /opt/dcc/src/dcc.tar.Z -C /opt/dcc/src && \
	mv /opt/dcc/src/dcc*/*  /opt/dcc/src/dcc*/.[^.]* /opt/dcc/

WORKDIR /opt/dcc/
RUN	./configure && \
	make && \
	make install
WORKDIR /

RUN	adduser --user-group --system --no-create-home --shell /bin/false clamav && \
	usermod -L clamav

#build cmake
RUN	mkdir -p /opt/cmake/src
RUN	wget -P /opt/cmake https://github.com/Kitware/CMake/releases/download/v3.17.3/cmake-3.17.3.tar.gz && \
	tar -zxvf /opt/cmake/cmake-3.17.3.tar.gz -C /opt/cmake/src
WORKDIR /opt/cmake/src/cmake-3.17.3
RUN	./bootstrap && \
	make && \
	make install
WORKDIR /

#build clamav
RUN	dnf --enablerepo=powertools -y install json-c-devel
RUN	dnf -y install libxml2 libxml2-devel bzip2 file libcurl-devel bzip2-devel check check-devel pcre2 pcre2-devel ncurses-devel
RUN	mkdir /opt/clamav && \
	git clone https://github.com/Cisco-Talos/clamav-devel.git /opt/clamav/src

WORKDIR /opt/clamav/src
#RUN	./configure --prefix=/opt/clamav/ --disable-clamonacc && \
#	make && \
#	make install
RUN	mkdir build && cd build
WORKDIR /opt/clamav/src/build
RUN	cmake .. -DENABLE_CLAMONACC=OFF -DCMAKE_INSTALL_PREFIX=/opt/clamav -DCMAKE_BUILD_TYPE="Release" -DENABLE_MILTER=OFF
RUN	cmake --build . --config Release
RUN	cmake --build . --config Release --target install
WORKDIR /

RUN	mkdir -p /opt/clamav/etc/ /var/log/clamav/ && \
	mkdir -p /var/run/clamd.scan/ && chown -R clamav.clamav /var/run/clamd.scan && \
	mkdir -p /var/lib/clamav/tmp && chown -R clamav.clamav /var/lib/clamav  && \
	touch  /var/log/clamav/freshclam.log && \
	touch  /var/log/clamav/clamd.log && \
	chown -R  clamav.clamav /var/log/clamav/ && \
	chmod -R 700 /var/log/clamav

COPY	freshclam.conf /opt/clamav/etc/
COPY	scan.conf /opt/clamav/etc/

COPY	exim/init.install /usr/local/bin/
COPY	exim/init.sql /usr/local/bin/
RUN	chmod -R 700 /usr/local/bin/init.install && \
	chmod -R 600 /usr/local/bin/init.sql

COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

RUN	usermod -a -G clamav exim && usermod -a -G exim clamav
RUN	mkdir -p  /var/tmp/clamav/ &&\
	chown -R clamav.clamav /var/tmp/clamav &&\
	chmod -R 777 /var/tmp/clamav

COPY	spamlearn /usr/local/bin/
RUN	chmod 700 /usr/local/bin/spamlearn
RUN	dnf -y install portreserve

COPY	rename /usr/local/bin/
RUN	chmod 700 /usr/local/bin/rename

WORKDIR /

#logrotate
COPY	exim.logrotate.conf /etc/logrotate.d/exim
COPY	spam.logrotate.conf /etc/logrotate.d/spam
RUN	dnf install logrotate -y && \
    sed -i -e '/dateext/s/^/#/' /etc/logrotate.conf && \
    echo "dateformat ''" >> /etc/logrotate.conf

RUN	dnf install cronie -y && \
	sed -i -e '/pam_loginuid.so/s/^/#/' /etc/pam.d/crond

RUN	echo "0   0  *  *  * logrotate -f /etc/logrotate.d/spam > /dev/null 2>&1" >> /etc/crontab && \
	echo "*/1 *  *  *  * /usr/bin/date > /tmp/status.cron" >> /etc/crontab && \
	crontab -u root /etc/crontab

RUN	echo "0   0  *  *  * logrotate -f /etc/logrotate.d/exim > /dev/null 2>&1" >> /etc/crontab.exim && \
	crontab -u exim /etc/crontab.exim

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 25

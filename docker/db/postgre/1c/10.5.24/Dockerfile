FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com
FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Postgre SQL Image" \
	image="postgre.1c.10.5.24" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-25"

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

RUN	export LANG=en_US.UTF-8

RUN	yum -y install libxslt libicu systemd-sysv libicu-devel tcl

COPY	install /opt/WWW/install

WORKDIR	/opt/WWW/install/postgresql-10.5-24.1C_x86_64_rpm
RUN	rpm -ivh postgresql10-1c-libs-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-contrib-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-server-10.5-24.el7.x86_64.rpm

WORKDIR	/opt/WWW/install/postgresql-10.5-24.1C_x86_64_addon_rpm
RUN	rpm -ivh postgresql10-1c-debuginfo-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-docs-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-pltcl-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-devel-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-test-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-plpython-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-plperl-10.5-24.el7.x86_64.rpm

#RUN	groupadd psql && \
#	useradd -g postgres -s /bin/bash -d /home/postgres postgres && \
#	echo 'postgres' |passwd postgres --stdin

RUN	rm -rf /opt/WWW/install
WORKDIR	/

RUN	localedef -c -f UTF-8 -i ru_RU ru_RU.UTF-8

ADD	postgres /usr/local/bin/
RUN	chmod +x /usr/local/bin/postgres

RUN	mv /usr/pgsql-10/bin/postgresql-10-setup{,.bak}
ADD	postgresql-10-setup /usr/pgsql-10/bin
RUN	chmod +x /usr/pgsql-10/bin/postgresql-10-setup

ENTRYPOINT ["/usr/bin/supervisord","-n"]

EXPOSE 22 5432

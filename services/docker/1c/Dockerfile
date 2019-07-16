FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-16"

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

#1c install
COPY	install /opt/WWW/install
WORKDIR	/opt/WWW/install
RUN	rpm -ivh 1C_Enterprise83-common-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-common-nls-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-ws-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-ws-nls-8.3.13-1644.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-server-8.3.13-1644.x86_64.rpm && \
	rm -rf /opt/WWW/install
WORKDIR	/

#1c configs
COPY	deploy /etc/httpd/deploy
RUN	touch /etc/httpd/conf.d/1c.conf && mkdir /opt/WWW/1c.ite-ng.ru

RUN	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir upp2012 -dir /opt/WWW/1c.ite-ng.ru/upp2012/ -connstr "Srvr=172.20.1.26;Ref=upp2012;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_upp2012.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir testzp  -dir /opt/WWW/1c.ite-ng.ru/testzp/  -connstr "Srvr=172.20.1.26;Ref=testzp;"  -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_testzp.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir doc2017 -dir /opt/WWW/1c.ite-ng.ru/doc2017/ -connstr "Srvr=172.20.1.26;Ref=doc2017;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_doc2017.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir upp_nek -dir /opt/WWW/1c.ite-ng.ru/upp_nek/ -connstr "Srvr=172.20.1.26;Ref=upp_nek;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_upp_nek.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir doc_nek2017 -dir /opt/WWW/1c.ite-ng.ru/doc_nek2017/ -connstr "Srvr=172.20.1.26;Ref=doc_nek2017;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_doc_nek2017.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir upp_kzkt -dir /opt/WWW/1c.ite-ng.ru/upp_kzkt/ -connstr "Srvr=172.20.1.26;Ref=upp_kzkt;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_upp_kzkt.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir ERP_oper_test -dir /opt/WWW/1c.ite-ng.ru/ERP_oper_test/ -connstr "Srvr=172.20.1.26;Ref=ERP_oper_test;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_ERP_oper_test.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir doc_oper_test -dir /opt/WWW/1c.ite-ng.ru/doc_oper_test/ -connstr "Srvr=172.20.1.26;Ref=doc_oper_test;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_doc_oper_test.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir erp_oper -dir /opt/WWW/1c.ite-ng.ru/erp_oper/ -connstr "Srvr=172.20.1.26;Ref=erp_oper;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_erp_oper.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir erp_nek2019 -dir /opt/WWW/1c.ite-ng.ru/erp_nek2019/ -connstr "Srvr=172.20.1.26;Ref=erp_nek2019;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_erp_nek2019.vrd

RUN	chown -R apache.apache /opt/WWW/1c.ite-ng.ru

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

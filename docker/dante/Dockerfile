FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

ENV	DANTE_VER 1.4.2

LABEL \
	name="evrinoma Dante Image" \
	image="Dante $DANTE_VER" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2018-04-17"

#epel
RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm;

RUN	yum -y install mc wget && \
	yum -y install iproute && \
	yum -y install openssh-server && \
	yum -y install supervisor && \
	yum -y install git && \
	yum -y install gcc && \
	yum -y install make;

RUN	yum -y update && \
	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";


ENV	USER root
ENV	PASSWORD 0631
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;

#build dante
RUN	mkdir /opt/dante && \
	wget -P /opt/dante https://www.inet.no/dante/files/dante-$DANTE_VER.tar.gz && \
	tar -xvf  /opt/dante/dante-$DANTE_VER.tar.gz -C  /opt/dante;
WORKDIR /opt/dante/dante-$DANTE_VER
RUN	./configure && \
	make && \
	make install;
RUN	rm -rf mkdir /opt/dante;
WORKDIR /

COPY	sockd.conf /etc/sockd.conf

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

ENV	USER proxy
ENV	PASSWORD proxy
#user
RUN	useradd --shell /usr/sbin/nologin $USER && \
	echo $PASSWORD | passwd --stdin $USER;

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 50080

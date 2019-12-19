FROM \
		centos:centos7

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma ruby265 Image" \
		image="ruby265" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2019-12-19"

RUN	yum -y install mc && \
	yum -y install git && \
	yum -y install httpd && \
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

RUN	yum -y install which zlib zlib-devel openssl-devel  ImageMagick-devel  ImageMagick mariadb-devel
RUN	yum -y groupinstall "Development Tools"

RUN	mkdir -p /opt/ruby && \
    cd /opt/ruby  && \
	wget https://cache.ruby-lang.org/pub/ruby/2.6/ruby-2.6.5.tar.gz --no-check-certificate && \
	tar -xzvf ruby-* && \
	cd ruby-* && \
	./configure && \
	make && \
	make install

RUN gem update --system && gem update

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

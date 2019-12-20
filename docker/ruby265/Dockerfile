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

RUN	yum -y install which \
	zlib \
	zlib-devel \
	openssl-devel  \
	ImageMagick-devel  \
	ImageMagick mariadb-devel \
	curl \
	httpd-devel \
	curl-devel \
	curl \
	gpg \
	gcc-c++

RUN yum -y remove ruby

RUN gpg --keyserver hkp://keys.gnupg.net --recv-keys 409B6B1796C275462A1703113804BB82D39DC0E3
RUN $(curl -sSL https://get.rvm.io | bash -s stable 2>&1| grep '\-\-keyserver') && curl -sSL https://get.rvm.io | bash -s stable

RUN /usr/local/rvm/bin/rvm install ruby-2.6.5
ENV PATH="/usr/local/rvm/bin:/usr/local/rvm/rubies/ruby-2.6.5/bin:${PATH}"
RUN echo "export PATH=$PATH" > /etc/environment
RUN ruby --version
RUN gem update --system && gem update

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

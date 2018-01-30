FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.0 Image" \
	image="php-7" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-12-30"

RUN	yum -y install mc wget && \
	yum -y install httpd && \
	yum -y install iproute && \
	yum -y update;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install php71w \
	php71w-cli \
	php71w-fpm \
	php71w-bcmath \
	php71w-common \
	php71w-gd \
	php71w-gmp \
	php71w-imap \
	php71w-intl \
	php71w-ldap \
	php71w-mbstring \
	php71w-mcrypt \
	php71w-mysqli \
	php71w-mysqlnd \
	php71w-opcache \
	php71w-pdo \
	php71w-pear \
	php71w-pgsql \
	php71w-pspell \
	php71w-recode \
	php71w-soap \
	php71w-tidy \
	php71w-xml \
	php71w-xmlrpc \
	php71w-pecl-apcu \
	php71w-pecl-imagick \
	php71w-pecl-memcached \
	php71w-pecl-mongodb \
	php71w-pecl-xdebug \
	php71w-pecl-zip;

RUN	yum -y install git \
	openssh-server \
	supervisor;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa &&\
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa &&\
	echo 0631 | passwd --stdin root;

# edit php.ini 
RUN sed -i -e "s/^\(short_open_tag =\).*$/\1 On/g" /etc/php.ini
RUN sed -i -e "s/DirectoryIndex \(.\+\)/DirectoryIndex index.php \1/g" /etc/httpd/conf/httpd.conf

RUN pwd
RUN echo pwd

#apache config
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/
COPY	vhost.conf /etc/httpd/conf.d/vhost.conf
RUN	ls /etc/httpd/conf.d/

#composer
RUN	curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
RUN	ls /usr/local/bin/

#CMD exec /usr/sbin/apachectl -D FOREGROUND
ENTRYPOINT ["/usr/sbin/apachectl", "-D", "FOREGROUND"]

#specify the port number the container should expose
EXPOSE 80

#CMD exec /usr/sbin/sshd -D
ENTRYPOINT ["/usr/sbin/sshd", "-D"]

EXPOSE 22

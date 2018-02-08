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

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	openssh-server \
	supervisor;

RUN	yum -y install php72w \
	php72w-bcmath \
	php72w-cli \
	php72w-common \
	php72w-dba \
	php72w-devel \
	php72w-embedded \
	php72w-enchant \
	php72w-fpm \
	php72w-gd \
	php72w-imap \
	php72w-interbase \
	php72w-intl \
	php72w-ldap \
	php72w-mbstring \
	php72w-mysql \
	php72w-odbc \
	php72w-opcache \
	php72w-pdo \
	php72w-pdo_dblib \
	php72w-pear \
	php72w-pecl-apcu \
	php72w-pecl-geoip \
	php72w-pecl-igbinary \
	php72w-pecl-imagick \
	php72w-pecl-libsodium \
	php72w-pecl-memcached \
	php72w-pecl-mongodb \
	php72w-pgsql \
	php72w-phpdbg \
	php72w-process \
	php72w-pspell \
	php72w-recode \
	php72w-snmp \
	php72w-soap \
	php72w-tidy \
	php72w-xml \
	php72w-xmlrpc;

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn;

RUN	yum -y install phpMyAdmin;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

RUN	mkdir /opt/xdebug && \
	wget -P /opt/xdebug https://pecl.php.net/get/xdebug-2.6.0.tgz && \
	tar -xvf  /opt/xdebug/xdebug-2.6.0.tgz -C  /opt/xdebug;

WORKDIR /opt/xdebug/xdebug-2.6.0

RUN	/usr/bin/phpize && \
	./configure --enable-xdebug && \
	make && \
	make install && \
	rm -rf mkdir /opt/xdebug/

WORKDIR /

#composer
RUN	curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
RUN	ls /usr/local/bin/

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa &&\
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa &&\
	echo 0631 | passwd --stdin root;
#httpd
RUN	sed -i -e "s/^\(short_open_tag =\).*$/\1 On/g" /etc/php.ini
RUN	sed -i -e "s/DirectoryIndex \(.\+\)/DirectoryIndex index.php \1/g" /etc/httpd/conf/httpd.conf
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/
COPY	vhost.conf /etc/httpd/conf.d/vhost.conf
#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history
#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf
#php extension
RUN	echo "zend_extension=xdebug.so" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_host = 172.18.1.3" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_enable = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_connect_back=1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_port = 9000" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.extended_info = 1" >> /etc/php.d/xdebug.ini && \
#	echo "xdebug.scream = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_depth = 16" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_children = 256" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.max_nesting_level = 1024" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.idekey="PHPSTORM"" >> /etc/php.d/xdebug.ini;
#phpMyAdmin
RUN	sed -i -e "s/Require\ ip\ 127\.0\.0\.1/Require\ ip\ 172\.20\.1\.0\/24\ 172\.18\.1\.0\/24\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/Allow\ from\ 127\.0\.0\.1/Allow\ from\ 172\.20\.1\.0\/24\ 172\.18\.1\.0\/24\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf;

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80


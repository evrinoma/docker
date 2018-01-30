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

RUN	yum -y install php72w \
	php72w-cli \
	php72w-fpm \
	php72w-bcmath \
	php72w-common \
	php72w-gd \
	php72w-gmp \
	php72w-imap \
	php72w-intl \
	php72w-ldap \
	php72w-mbstring \
	php72w-mcrypt \
	php72w-mysqli \
	php72w-mysqlnd \
	php72w-opcache \
	php72w-pdo \
	php72w-pear \
	php72w-pgsql \
	php72w-pspell \
	php72w-recode \
	php72w-soap \
	php72w-tidy \
	php72w-xml \
	php72w-xmlrpc \
	php72w-pecl-apcu \
	php72w-pecl-imagick \
	php72w-pecl-memcached \
	php72w-pecl-mongodb \
	php72w-pecl-xdebug \
	php72w-pecl-zip;

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

COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";


#RUN	sed -i -e "s/^error_reporting\ =\ E_ALL\ \&\ \~E_DEPRECATED\ \&\ \~E_STRICT/error_reporting\ =\ E_ALL\ \&\ \~E_DEPRECATED\ \&\ \~\!E_STRICT/" /etc/php.ini
RUN	echo "xdebug.remote_host = 172.18.1.3" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_enable = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_connect_back=1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_port = 9000" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.extended_info = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.scream = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_depth = 16" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_children = 256" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.max_nesting_level = 1024" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.idekey="PHPSTORM"" >> /etc/php.d/xdebug.ini;

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

#CMD exec /usr/sbin/apachectl -D FOREGROUND
#ENTRYPOINT ["/usr/sbin/apachectl", "-D", "FOREGROUND"]
#EXPOSE 80
#CMD exec /usr/sbin/sshd -D
#ENTRYPOINT ["/usr/sbin/sshd", "-D"]
#EXPOSE 22

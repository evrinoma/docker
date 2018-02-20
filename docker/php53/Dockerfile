FROM \
	centos:centos6

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.3 Image" \
	image="php-5.3" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2017-02-20"

RUN	yum -y install mc wget && \
	yum -y install httpd && \
	yum -y install iproute && \
	yum -y update;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-6.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el6/latest.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	openssh-server \
	supervisor;

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn;

RUN	yum -y install php  \ 
	php-mysql \
	php-mbstring \
	php-gd \
	php-cli \
	php-ldap \
	php-pear \
	php-devel \
	php-mcrypt \
	php-mbstring \
	php-snmp \
	php-xml \
	php-bcmath \
	php-rrdtool \
	php-pdo \
	php-odbc \
	php-mssql \
	php-ldap \
	php-pecl-xdebug \
	php-pecl-apc \
	php-pecl-runkit \
	php-unit \
	php-phpunit \
	php-phpunit-PHPUnit \
	php-pecl-imagick \
	php-soap

RUN	yum -y install phpMyAdmin;

RUN	yum clean metadata && \
	yum clean all;

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
RUN	mv -v /etc/supervisord.conf{,.bak}
COPY	supervisord.conf /etc/supervisord.conf

#php extension
RUN	echo "xdebug.remote_host = 172.18.1.53" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_enable = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_connect_back=1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_port = 9000" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.extended_info = 1" >> /etc/php.d/xdebug.ini && \
##	echo "xdebug.scream = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_depth = 16" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_children = 256" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.max_nesting_level = 1024" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.idekey="PHPSTORM"" >> /etc/php.d/xdebug.ini;

#hpMyAdmin
RUN	sed -i -e "s/Require\ ip\ 127\.0\.0\.1/Require\ ip\ 172\.20\.1\.0\/24\ 172\.18\.1\.0\/24\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/Allow\ from\ 127\.0\.0\.1/Allow\ from\ 172\.20\.1\.0\/24\ 172\.18\.1\.0\/24\ 172\.20\.1\.0\/24\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/'localhost'\;/'172\.18\.1\.2'\;/" /etc/phpMyAdmin/config.inc.php;

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80


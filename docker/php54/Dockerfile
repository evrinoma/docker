FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.4 Image" \
	image="php54" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-18"

RUN	yum -y install mc wget && \
	yum -y install httpd && \
	yum -y install iproute;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
# && \
#	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	openssh-server \
	supervisor;

RUN	yum -y install \
	telnel \
	exim.x86_64 \
	mailx;

RUN	yum -y install php \
	php-bcmath \
	php-cli \
	php-common \
	php-dba \
	php-devel \
	php-embedded \
	php-enchant \
	php-fpm \
	php-gd \
	php-imap \
	php-interbase \
	php-intl \
	php-ldap \
	php-mbstring \
	php-mysql \
	php-odbc \
	php-opcache \
	php-pdo \
	php-pdo_dblib \
	php-pear \
	php-pecl-apcu \
	php-pecl-geoip \
	php-pecl-igbinary \
	php-pecl-imagick \
	php-pecl-libsodium \
	php-pecl-memcached \
	php-pecl-mongodb \
	php-pgsql \
	php-phpdbg \
	php-process \
	php-pspell \
	php-recode \
	php-snmp \
	php-soap \
	php-tidy \
	php-xml \
	php-xmlrpc \
	php-mcrypt \
	php-rrdtool \
	php-pear-DB.noarch

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn npm;

RUN	yum -y install phpMyAdmin;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;
#build xdebug
RUN	mkdir /opt/xdebug && \
	wget -P /opt/xdebug https://pecl.php.net/get/xdebug-2.4.1.tgz && \
	tar -xvf  /opt/xdebug/xdebug-2.4.1.tgz -C  /opt/xdebug;

WORKDIR /opt/xdebug/xdebug-2.4.1

RUN	/usr/bin/phpize && \
	./configure --enable-xdebug && \
	make && \
	make install && \
	rm -rf mkdir /opt/xdebug/

WORKDIR /

#composer
RUN	curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
RUN	ls /usr/local/bin/
RUN     ln -s /opt/WWW/container.ite-ng.ru/conf/.composer /root/.composer

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;
#php.ini
RUN	sed -i -e "s/^\(short_open_tag =\).*$/\1 On/g" /etc/php.ini && \
	sed -i -e "s/\;date\.timezone\ \=/date\.timezone\ \=\ \"Europe\/Moscow\"/g" /etc/php.ini && \
	sed -i -e "s/memory_limit\ \=\ 128M/memory_limit\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_input_time\ \=\ 60/max_input_time\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_execution_time\ \=\ 30/max_execution_time\ \=\ 10000/g" /etc/php.ini && \
	sed -i -e "s/post_max_size \=\ 8/post_max_size\ \=\ 4096/g" /etc/php.ini && \
	sed -i -e "s/upload_max_filesize \=\ 2/upload_max_filesize\ \=\ 4096/g" /etc/php.ini && \
	sed -i -e "s/session.gc_maxlifetime\ \=\ 1440/session.gc_maxlifetime\ \=\ 36000/g" /etc/php.ini
#apcu.ini
RUN     sed -i -e "s/apc\.shm_size\=64M/apc\.shm_size\=3G/g" /etc/php.d/apcu.ini
#httpd
RUN	sed -i -e "s/DirectoryIndex \(.\+\)/DirectoryIndex index.php \1/g" /etc/httpd/conf/httpd.conf
#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history
#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf
#php extension
RUN	echo "zend_extension=/usr/lib64/php/modules/xdebug.so" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_host = 172.18.54.54" >> /etc/php.d/xdebug.ini && \
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
RUN	sed -i -e "s/Require\ ip\ 127\.0\.0\.1/Require\ ip\ 172\.20\.1\.0\/24\ 172\.18\.0\.0\/16\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/Allow\ from\ 127\.0\.0\.1/Allow\ from\ 172\.20\.1\.0\/24\ 172\.18\.0\.0\/16\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/'localhost'\;/'172\.18\.2\.1'\;/" /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s/\?>//" /etc/phpMyAdmin/config.inc.php && \
	echo "?>" >> /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s/\?>/\$cfg\['Servers'\]\[\$i\]\['LoginCookieValidity'\]\ = 36000\;/" /etc/phpMyAdmin/config.inc.php
#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/
#httpd hosts
COPY	vhost.conf /etc/httpd/conf.d/vhost.conf
#mail
RUN	mv -v /etc/exim/exim.conf{,.bak} && \
	mv -v /usr/sbin/sendmail{,.bak} && \
	cp /usr/sbin/exim /usr/sbin/sendmail && \
	chmod 4755 /usr/sbin/sendmail && \
	mv /bin/mail{,.bak} && \
	ln -s /bin/mailx /bin/mail
#clean
RUN	rm -f /var/run/httpd/httpd.pid

COPY	exim.conf /etc/exim/exim.conf

#webpack
RUN	npm install -g webpack webpack-cli;

RUN	yum -y install bzip2 unzip

RUN	yum -y install patch

RUN	setcap 'cap_net_bind_service=+ep' /usr/sbin/httpd && \
	chown -R apache.apache /var/log/httpd/

COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

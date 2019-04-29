FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.0 Image" \
	image="php-7" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2018-11-09"

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

RUN	yum -y install telnel \
	exim.x86_64 \
	mailx;

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
#	php71w-mysqlnd \
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

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn npm;

RUN	yum -y install phpMyAdmin;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

#composer
RUN	curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
RUN	ls /usr/local/bin/
RUN	ln -s /opt/WWW/container.ite-ng.ru/.composer /root/.composer

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 0631 | passwd --stdin root;
#php.ini
RUN	sed -i -e "s/^\(short_open_tag =\).*$/\1 On/g" /etc/php.ini && \
	sed -i -e "s/\;date\.timezone\ \=/date\.timezone\ \=\ \"Europe\/Moscow\"/g" /etc/php.ini && \
	sed -i -e "s/memory_limit\ \=\ 128M/memory_limit\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_input_time\ \=\ 60/max_input_time\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_execution_time\ \=\ 30/max_execution_time\ \=\ 10000/g" /etc/php.ini && \
	sed -i -e "s/post_max_size \=\ 8/post_max_size\ \=\ 1024/g" /etc/php.ini && \
	sed -i -e "s/session.gc_maxlifetime\ \=\ 1440/session.gc_maxlifetime\ \=\ 36000/g" /etc/php.ini
#apcu.ini
RUN	sed -i -e "s/apc\.shm_size\=64M/apc\.shm_size\=3G/g" /etc/php.d/apcu.ini
#httpd
RUN	sed -i -e "s/DirectoryIndex \(.\+\)/DirectoryIndex index.php \1/g" /etc/httpd/conf/httpd.conf
#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history
#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf
#php extension
RUN	echo "zend_extension=xdebug.so" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_host = 172.18.71.71" >> /etc/php.d/xdebug.ini && \
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
	sed -i -e "s/'localhost'\;/'172\.18\.1\.2'\;/" /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s/\?>/\$cfg\['Servers'\]\[\$i\]\['LoginCookieValidity'\]\ = 36000\;/" /etc/phpMyAdmin/config.inc.php && \
	echo "?>" >> /etc/phpMyAdmin/config.inc.php;
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
RUN	echo "nameserver 172.20.1.5" > /etc/resolv.conf && \
	rm -f /var/run/httpd/httpd.pid

COPY	exim.conf /etc/exim/exim.conf

RUN	npm install -g webpack webpack-cli;

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80

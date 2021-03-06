FROM \
	centos:centos6

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.3 Image" \
	image="php53" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-01-05"

ONBUILD	ARG	MODE=''

RUN	sed -i -e "s@mirrorlist\=@#mirrorlist\=@g" /etc/yum.repos.d/CentOS-Base.repo && \
	sed -i -e "s@#baseurl=http://mirror.centos.org/centos/\$releasever/@baseurl=https://vault.centos.org/6.10/@g" /etc/yum.repos.d/CentOS-Base.repo

RUN	yum -y install mc wget  httpd iproute epel-release && \
	yum -y update;

RUN	rpm -Uvh https://mirror.webtatic.com/yum/el6/latest.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	openssh-server \
	supervisor;

RUN	yum -y install \
	telnel \
	exim.x86_64 \
	mailx;

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn npm;

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
#	php-pecl-xdebug \
	php-pecl-apc \
	php-pecl-runkit \
	php-unit \
	php-phpunit \
	php-phpunit-PHPUnit \
	php-pecl-imagick \
	php-soap

RUN	yum -y install phpMyAdmin;

#install php 5.3.29
RUN	wget -O /etc/yum.repos.d/centosup.repo http://centosup.ispsystem.info/centosup.repo;
RUN	yum makecache && \ 
	yum -y update php-\*

RUN	yum clean metadata && \
	yum clean all;

RUN wget -P /opt/dbase http://pecl.php.net/get/dbase-5.0.1.tgz && \
    pecl install /opt/dbase/dbase-5.0.1.tgz && \
    echo "extension=dbase.so" >/etc/php.d/dbase.ini && \
    rm -rf /opt/dbase

#build xdebug 2.2.7 support php 5.3.29
RUN	mkdir /opt/xdebug && \
	wget -P /opt/xdebug https://xdebug.org/files/xdebug-2.2.7.tgz && \
	tar -xvf  /opt/xdebug/xdebug-2.2.7.tgz -C  /opt/xdebug;

WORKDIR	/opt/xdebug/xdebug-2.2.7

RUN	/usr/bin/phpize && \
	./configure --enable-xdebug && \
	make && \
	make install && \
	rm -rf mkdir /opt/xdebug/

WORKDIR /

RUN	curl https://packages.microsoft.com/config/rhel/6/prod.repo > /etc/yum.repos.d/mssql-release.repo
RUN	ACCEPT_EULA=Y yum -y install msodbcsql17

#composer
RUN	curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
RUN	ls /usr/local/bin/
RUN	ln -s /opt/WWW/container.ite-ng.ru/conf/.composer /root/.composer

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;
#freetds
RUN	if [ -f "/etc/freetds.conf" ]; then sed -i -e "s@.*tds version \=*@\ttds version \= @g" -i -e "s@\(tds version \=\).*@\1 7\.0@g"  /etc/freetds.conf ; fi
#php.ini
RUN	sed -i -e "s/^\(short_open_tag =\).*$/\1 On/g" /etc/php.ini && \
	sed -i -e "s/\;date\.timezone\ \=/date\.timezone\ \=\ \"Europe\/Moscow\"/g" /etc/php.ini && \
	sed -i -e "s/memory_limit\ \=\ 128M/memory_limit\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_input_time\ \=\ 60/max_input_time\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_execution_time\ \=\ 30/max_execution_time\ \=\ 10000/g" /etc/php.ini && \
	sed -i -e "s/post_max_size \=\ 8/post_max_size\ \=\ 4096/g" /etc/php.ini && \
	sed -i -e "s/upload_max_filesize \=\ 2/upload_max_filesize\ \=\ 4096/g" /etc/php.ini && \
	sed -i -e "s/session.gc_maxlifetime\ \=\ 1440/session.gc_maxlifetime\ \=\ 36000/g" /etc/php.ini && \
    sed -i -e 's@\(;mssql.charset \= \).*@mssql.charset \= "UTF-8"@g' /etc/php.ini

#apc.ini
RUN	sed -i -e "s/apc\.shm_size\=64M/apc\.shm_size\=3G/g" /etc/php.d/apc.ini && \
	sed -i -e "s/apc\.max_file_size\=1M/apc\.max_file_size\=64M/g" /etc/php.d/apc.ini
#httpd
RUN	sed -i -e "s/DirectoryIndex \(.\+\)/DirectoryIndex index.php \1/g" /etc/httpd/conf/httpd.conf
#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history
#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf && \
	cat /etc/supervisord.d/supervisor.conf >> /etc/supervisord.conf
#	doesn't work 
#	echo '[include]' >> /etc/supervisord.conf && \
#	echo 'files = /etc/supervisord.d/supervisor.conf' >> /etc/supervisord.conf
#php extension
RUN	echo "xdebug.remote_host = 172.18.53.53" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_enable = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_connect_back=1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_port = 9000" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.extended_info = 1" >> /etc/php.d/xdebug.ini && \
##	echo "xdebug.scream = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_depth = 16" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_children = 256" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.max_nesting_level = 1024" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.idekey="PHPSTORM"" >> /etc/php.d/xdebug.ini;
#phpMyAdmin
RUN	sed -i -e "s/Require\ ip\ 127\.0\.0\.1/Require\ ip\ 172\.20\.0\.0\/16\ 172\.18\.0\.0\/16\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/Allow\ from\ 127\.0\.0\.1/Allow\ from\ 172\.20\.0\.0\/16\ 172\.18\.0\.0\/16\ 127\.0\.0\.1/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/'localhost'\;/'172\.18\.2\.1'\;/" /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s/\?>//" /etc/phpMyAdmin/config.inc.php && \
	echo "\$cfg['LoginCookieValidity'] = 1440;" >> /etc/phpMyAdmin/config.inc.php && \
	echo "\$cfg['ExecTimeLimit'] = 0;" >> /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s@\['LoginCookieValidity'\] \= 1440@\['LoginCookieValidity'\] \= 36000@" /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s/Alias\ \/phpMyAdmin\ \/usr\/share\/phpMyAdmin//" /etc/httpd/conf.d/phpMyAdmin.conf && \
	sed -i -e "s/phpMyAdmin/hidden/" /etc/httpd/conf.d/phpMyAdmin.conf && \
	mv /usr/share/phpMyAdmin /usr/share/hidden && \
	sed -i -e "s/Alias\ \/phpmyadmin/Alias\ \/covert/" /etc/httpd/conf.d/phpMyAdmin.conf
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

RUN	yum -y install bzip2 unzip

RUN	yum -y install patch

COPY	exim.conf /etc/exim/exim.conf

RUN	setcap 'cap_net_bind_service=+ep' /usr/sbin/httpd && \
	chown -R apache.apache /var/log/httpd/

COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

RUN	npm config set strict-ssl false && npm install -g n && n 10
#&& n 6.17.1

RUN	yum install cronie -y && \
	sed -i -e '/pam_loginuid.so/s/^/#/' /etc/pam.d/crond && \
	touch /etc/crontab.apache
RUN	if [ -f "/etc/security/limits.d/90-nproc.conf" ] ; then sed -i 's/1024/4096/g' /etc/security/limits.d/90-nproc.conf ; else echo '*          soft    nproc     4096' >  /etc/security/limits.d/90-nproc.conf && echo 'root       soft    nproc     unlimited' >>  /etc/security/limits.d/90-nproc.conf; fi;

COPY	reconfig /usr/local/bin/
RUN	chmod +x /usr/local/bin/reconfig

#webpack
RUN	npm install -g webpack webpack-cli;

COPY	restartHttpd /usr/local/bin/
RUN	chmod +x /usr/local/bin/restartHttpd

COPY	autoDevDeploy /usr/local/bin/
RUN	chmod +x /usr/local/bin/autoDevDeploy

COPY    install/ /root/install


ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 80


FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 8 Image" \
	image="php8" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-09-16"

ONBUILD	ARG	MODE=''

RUN	yum -y install mc wget && \
	yum -y install httpd && \
	yum -y install nginx && \
	yum -y install iproute;

RUN	dnf -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
	dnf -y install https://rpms.remirepo.net/enterprise/remi-release-8.rpm

RUN	yum -y install gcc \
	make \
	glibc-langpack-en;

RUN	yum -y install git \
	passwd \
	supervisor \
	openssh-server;

RUN	yum -y install \
	telnet \
	exim.x86_64 \
	mailx \
	bzip2 \
	unzip \
	patch;

RUN	dnf module enable php:remi-8.0 -y
RUN	dnf install -y php php-dom php-simplexml php-ssh2 php-xml php-xmlreader php-curl php-date php-exif php-filter php-ftp php-gd php-hash php-iconv php-json php-libxml php-pecl-imagick php-mbstring php-mysqlnd php-openssl php-pcre php-posix php-sockets php-spl php-tokenizer php-zlib php-pear php-pdo php-session php-devel

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn npm;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;
#build xdebug
RUN	mkdir /opt/xdebug && \
	wget -P /opt/xdebug https://pecl.php.net/get/xdebug-3.0.4.tgz && \
	tar -xvf  /opt/xdebug/xdebug-3.0.4.tgz -C  /opt/xdebug;

WORKDIR /opt/xdebug/xdebug-3.0.4

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

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	mkdir -p /run/supervisor/ && \
	#sed -i -e "s@^nodaemon=false@nodaemon=true@" /etc/supervisord.conf && \
	sed -i -e "s@^files\ =\ supervisord\.d\/\*\.ini@files\ =\ supervisord\.d\/\*\.conf@" /etc/supervisord.conf && \
	sed -i -e "s@^\[unix_http_server\]@;\[unix_http_server\]@" /etc/supervisord.conf && \
	sed -i -e "s@^file\=\/run\/supervisor\/supervisor.sock@;&@" /etc/supervisord.conf && \
    sed -i -e "s@^;\[inet_http_server\]@\[inet_http_server\]@" /etc/supervisord.conf && \
    sed -i -e "s@^;port=127\.0\.0\.1\:9001@port=127\.0\.0\.1\:9001@" /etc/supervisord.conf && \
	sed -i -e "s@^serverurl=unix\:@;&@" /etc/supervisord.conf && \
	sed -i -e "s@^;serverurl=http:@serverurl=http:@" /etc/supervisord.conf

##mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

#php.ini
RUN	sed -i -e "s/^\(short_open_tag =\).*$/\1 On/g" /etc/php.ini && \
	sed -i -e "s/\;date\.timezone\ \=/date\.timezone\ \=\ \"Europe\/Moscow\"/g" /etc/php.ini && \
	sed -i -e "s/\;max_input_vars\ \=/max_input_vars\ \=\ 16000 \n\;/g" /etc/php.ini && \
	sed -i -e "s/memory_limit\ \=\ 128M/memory_limit\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_input_time\ \=\ 60/max_input_time\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_execution_time\ \=\ 30/max_execution_time\ \=\ 10000/g" /etc/php.ini && \
	sed -i -e "s/post_max_size \=\ 8/post_max_size\ \=\ 8192/g" /etc/php.ini && \
	sed -i -e "s/upload_max_filesize \=\ 2/upload_max_filesize\ \=\ 4096/g" /etc/php.ini && \
	sed -i -e "s/session.gc_maxlifetime\ \=\ 1440/session.gc_maxlifetime\ \=\ 36000/g" /etc/php.ini && \
	sed -i -e "s/expose_php\ \=\ On/expose_php\ \=\ off/" /etc/php.ini

#httpd
RUN	sed -i -e "s/DirectoryIndex \(.\+\)/DirectoryIndex index.php \1/g" /etc/httpd/conf/httpd.conf && \
	sed -i -e "s/IncludeOptional\ conf\.d\/\*\.conf//" /etc/httpd/conf/httpd.conf && \
	echo 'TraceEnable Off' >> /etc/httpd/conf/httpd.conf && \
	echo 'ServerTokens Prod' >> /etc/httpd/conf/httpd.conf && \
	echo 'ServerSignature Off' >> /etc/httpd/conf/httpd.conf && \
	echo 'Header always append X-Frame-Options ite-ng.ru' >> /etc/httpd/conf/httpd.conf && \
	echo 'Header set X-XSS-Protection "1; mode=block"' >> /etc/httpd/conf/httpd.conf && \
	echo 'Header set X-Content-Type-Options nosniff' >> /etc/httpd/conf/httpd.conf && \
	echo 'IncludeOptional conf.d/*.conf' >> /etc/httpd/conf/httpd.conf && \
    echo 'TimeOut 600' >> /etc/httpd/conf/httpd.conf

##php extension
RUN	touch /etc/php.d/xdebug.ini && \
	echo "zend_extension=xdebug.so" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.start_upon_error = yes" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.start_with_request=yes" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.client_port = 9003" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.mode = debug" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.client_host = 172.18.80.80" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.idekey=PHPSTORM" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.discover_client_host=1" >> /etc/php.d/xdebug.ini

RUN	sed -i -e "s/Options\ Indexes\ MultiViews\ FollowSymlinks/#&/" /etc/httpd/conf.d/autoindex.conf  && \
	sed -i -e "s/AllowOverride None/AllowOverride All/" /etc/httpd/conf.d/autoindex.conf && \
	sed -i -e "s/Require\ all\ granted/#&\n/" /etc/httpd/conf.d/autoindex.conf && \
	sed -i -e "s/#Require\ all\ granted/&\nOrder\ deny,allow\nAllow\ from\ 172\.20\.1\.0\/24\ 172\.18\.0\.0\/16\ 127\.0\.0\.1/" /etc/httpd/conf.d/autoindex.conf

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

#php-fpm
RUN	mkdir -p /run/php-fpm && touch /run/php-fpm/www.sock

#exim
COPY	exim.conf /etc/exim/exim.conf

#webpack
RUN	npm install -g webpack webpack-cli;

#mssql php
RUN	curl https://packages.microsoft.com/config/rhel/8/prod.repo > /etc/yum.repos.d/mssql-release.repo && \
	ACCEPT_EULA=Y yum install msodbcsql17 -y && \
	ACCEPT_EULA=Y yum install mssql-tools -y
RUN yum install -y unixODBC-devel php-sqlsrv
RUN	echo 'export PATH="$PATH:/opt/mssql-tools/bin"' >> ~/.bash_profile && \
	echo 'export PATH="$PATH:/opt/mssql-tools/bin"' >> ~/.bashrc && \
	source ~/.bashrc
RUN mv /etc/pki/tls/openssl.cnf{,.bak} && \
    echo "openssl_conf = default_conf" >> /etc/pki/tls/openssl.cnf && \
    echo "[default_conf]" >> /etc/pki/tls/openssl.cnf && \
    echo "ssl_conf = ssl_sect" >> /etc/pki/tls/openssl.cnf && \
    echo "" >> /etc/pki/tls/openssl.cnf && \
    echo "[ssl_sect]" >> /etc/pki/tls/openssl.cnf && \
    echo "system_default = system_default_sect" >> /etc/pki/tls/openssl.cnf && \
    echo "" >> /etc/pki/tls/openssl.cnf && \
    echo "[system_default_sect]" >> /etc/pki/tls/openssl.cnf && \
    echo "CipherString = DEFAULT@SECLEVEL=1" >> /etc/pki/tls/openssl.cnf

RUN	setcap 'cap_net_bind_service=+ep' /usr/sbin/httpd && \
	chown -R apache.apache /var/log/httpd/

COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container

RUN	yum install cronie -y && \
	sed -i -e '/pam_loginuid.so/s/^/#/' /etc/pam.d/crond && \
	touch /etc/crontab.apache

COPY	reconfig /usr/local/bin/
RUN	chmod +x /usr/local/bin/reconfig

COPY	restartNginx /usr/local/bin/
RUN	chmod +x /usr/local/bin/restartNginx

COPY	restartHttpd /usr/local/bin/
RUN	chmod +x /usr/local/bin/restartHttpd

COPY	restartPhpFpm /usr/local/bin/
RUN	chmod +x /usr/local/bin/restartPhpFpm

COPY	autoDevDeploy /usr/local/bin/
RUN	chmod +x /usr/local/bin/autoDevDeploy

COPY    install/ /root/install

ENTRYPOINT ["/usr/bin/supervisord"]
EXPOSE 22 80

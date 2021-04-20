FROM \
	centos:centos8

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 Image" \
	image="php74" \
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

RUN	dnf module enable php:remi-7.4 -y

RUN	dnf install -y php php-dom \
    php-simplexml \
    php-ssh2 \
    php-xml \
    php-xmlreader \
    php-curl \
    php-date \
    php-exif \
    php-filter \
    php-ftp \
    php-gd \
    php-hash \
    php-iconv \
    php-json \
    php-libxml \
    php-pecl-imagick \
    php-mbstring \
    php-mysqlnd \
    php-openssl \
    php-pcre \
    php-posix \
    php-sockets \
    php-spl \
    php-tokenizer \
    php-zlib \
    php-pear \
    php-pdo \
    php-session

RUN	dnf install -y php-ldap \
    php-xhprof \
    php-ast \
    php-cli \
    php-dba \
    php-dbg \
    php-ffi \
    php-fpm \
    php-gmp \
    php-lz4 \
    php-pdo \
    php-geos \
    php-imap \
    php-intl \
    php-ldap \
    php-odbc \
    php-pggi \
    php-snmp \
    php-soap \
    php-tidy \
    php-zstd \
    php-devel \
    php-pdlib \
    php-pgsql \
    php-pinba \
    php-bcmath \
    php-brotli \
    php-common \
    php-pspell \
    php-snappy \
    php-sodium \
    php-xmlrpc \
    php-enchant \
    php-libvirt \
    php-opcache \
    php-pecl-apcu-bc \
    php-pecl-memcache \
    php-pecl-redis5 \
    php-pecl-zip
#    php-pecl-ds \
#    php-pecl-ev \
#    php-pecl-pq \
#    php-pecl-uv \
#    php-process \
#    php-embedded \
#    php-pecl-csv \
#    php-pecl-dio \
#    php-pecl-eio \
#    php-pecl-env \
#    php-pecl-ice \
#    php-pecl-lua \
#    php-pecl-lzf \
#    php-pecl-nsq \
#    php-pecl-pcs \
#    php-pecl-psr \
#    php-pecl-rar \
#    php-pecl-rrd \
#    php-pecl-svn \
#    php-pecl-vld \
#    php-pecl-yac \
#    php-pecl-yaf \
#    php-pecl-yar \
#    php-pecl-yaz \
#    php-pecl-zmq \
#    php-phalcon4 \
#    php-componere \
#    php-litespeed \
#    php-maxminddb \
#    php-pdo-dblib \
#    php-pecl-amqp \
#    php-pecl-apcu \
#    php-pecl-apfd \
#    php-pecl-fann \
#    php-pecl-grpc \
#    php-pecl-http \
#    php-pecl-krb5 \
#    php-pecl-pcov \
#    php-pecl-ssh2 \
#    php-pecl-sync \
#    php-pecl-trie \
#    php-pecl-uopz \
#    php-pecl-uuid \
#    php-pecl-wddx \
#    php-pecl-yaml \
#    php-phpiredis \
#    php-smbclient \
#    php-wkhtmltox \
#    php-pecl-cmark \
#    php-pecl-dbase \
#    php-pecl-druid \
#    php-pecl-event \
#    php-pecl-geoip \
#    php-pecl-gnupg \
#    php-pecl-mysql \
#    php-pecl-oauth \
#    php-pecl-parle \
#    php-pecl-raphf \
#    php-pecl-solr2 \
#    php-pecl-stats \
#    php-pecl-stomp \
#    php-pecl-taint \
#    php-pecl-xattr \
#    php-pecl-xdiff \
#    php-pecl-xxtea \
#    php-libvirt-doc \
#    php-pecl-base58 \
#    php-pecl-bitset \
#    php-pecl-crypto \
#    php-pecl-gender \
#    php-pecl-hprose \
#    php-pecl-hrtime \
#    php-pecl-mcrypt \
#    php-pecl-pdflib \
#    php-pecl-propro \
#    php-pecl-radius \
#    php-pecl-recode \
#    php-pecl-redis4 \
#    php-pecl-redis5 \
#    php-pecl-scrypt \
#    php-pecl-sphinx \
#    php-pecl-ssdeep \
#    php-pecl-trader \
#    php-pecl-xhprof \
#    php-pecl-yaconf \
#    php-pdo-firebird \
#    php-pecl-decimal \
#    php-pecl-gearman \
#    php-pecl-inotify \
#    php-pecl-leveldb \
#    php-pecl-memprof \
#    php-pecl-mongodb \
#    php-pecl-msgpack \
#    php-pecl-rdkafka \
#    php-pecl-request \
#    php-pecl-rpminfo \
#    php-pecl-runkit7 \
#    php-pecl-seaslog \
#    php-pecl-selinux \
#    php-pecl-swoole4 \
#    php-pecl-timecop \
#    php-pecl-varnish \
#    php-pecl-xmldiff \
#    php-pecl-igbinary \
#    php-pecl-mogilefs \
#    php-pecl-mustache \
#    php-pecl-protobuf \
#    php-pecl-rdkafka4 \
#    php-pecl-scoutapm \
#    php-pecl-translit \
#    php-snuffleupagus \
#    php-zephir-parser \
#    php-ioncube-loader \
#    php-pecl-cassandra \
#    php-pecl-json-post \
#    php-pecl-mailparse \
#    php-pecl-memcached \
#    php-pecl-mosquitto \
#    php-pecl-pcs-devel \
#    php-pecl-seasclick \
#    php-pecl-xlswriter \
#    php-realpath-turbo \
#    php-pecl-apcu-devel \
#    php-pecl-couchbase2 \
#    php-pecl-geospatial \
#    php-pecl-handlebars \
#    php-pecl-http-devel \
#    php-pecl-krb5-devel \
#    php-pecl-luasandbox \
#    php-pecl-opencensus \
#    php-pecl-skywalking \
#    php-pecl-ahocorasick \
#    php-pecl-ip2location \
#    php-pecl-http-message \
#    php-pecl-datadog-trace \
#    php-pecl-hdr-histogram \
#    php-pecl-imagick-devel \
#    php-pecl-msgpack-devel \
#    php-pecl-mysql-xdevapi \
#    php-pecl-mysqlnd-azure \
#    php-pecl-igbinary-devel \
#    php-pecl-uploadprogress

RUN	wget https://dl.yarnpkg.com/rpm/yarn.repo -O /etc/yum.repos.d/yarn.repo && \
	yum -y install yarn npm;

RUN	yum -y autoremove && \
	yum clean metadata && \
	yum clean all;

#build xdebug
RUN	mkdir /opt/xdebug && \
	wget -P /opt/xdebug https://pecl.php.net/get/xdebug-2.9.6.tgz && \
	tar -xvf  /opt/xdebug/xdebug-2.9.6.tgz -C  /opt/xdebug;

WORKDIR /opt/xdebug/xdebug-2.9.6

RUN	/usr/bin/phpize && \
	./configure --enable-xdebug && \
	make && \
	make install && \
	rm -rf mkdir /opt/xdebug/

WORKDIR /

#The "symfony/flex" plugin was skipped because it is not compatible with Composer 2+.
#RUN	curl -sS https://getcomposer.org/installer | php && mv composer.phar /usr/local/bin/composer
RUN	wget https://getcomposer.org/composer-1.phar -O /usr/local/bin/composer && chmod 755 /usr/local/bin/composer
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
	sed -i -e "s/memory_limit\ \=\ 128M/memory_limit\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_input_time\ \=\ 60/max_input_time\ \=\ -1/g" /etc/php.ini && \
	sed -i -e "s/max_execution_time\ \=\ 30/max_execution_time\ \=\ 10000/g" /etc/php.ini && \
	sed -i -e "s/post_max_size \=\ 8/post_max_size\ \=\ 4096/g" /etc/php.ini && \
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
	echo "xdebug.remote_host = 172.18.72.72" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_enable = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_connect_back=1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.remote_port = 9000" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.extended_info = 1" >> /etc/php.d/xdebug.ini && \
#	echo "xdebug.scream = 1" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_depth = 16" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.var_display_max_children = 256" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.max_nesting_level = 1024" >> /etc/php.d/xdebug.ini && \
	echo "xdebug.idekey="PHPSTORM"" >> /etc/php.d/xdebug.ini;

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

COPY	autoDevDeploy /usr/local/bin/
RUN	chmod +x /usr/local/bin/autoDevDeploy

COPY    install/ /root/install

ENTRYPOINT ["/usr/bin/supervisord"]
EXPOSE 22 80

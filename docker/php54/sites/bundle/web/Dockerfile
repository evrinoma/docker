FROM \
	evrinoma/php54:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 5.4 web" \
	image="php54.web" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-18"

COPY	nekeng/install/nekeng.sql.tar.gz /root/
COPY	nekeng/install/nekeng.tar.gz /root/
ADD	nekeng/nekeng/init.install /usr/local/bin/init.install.nekeng
ADD	nekeng/nekeng/init.sql /usr/local/bin/init.sql.nekeng
RUN	chmod +x /usr/local/bin/init.install.nekeng && \
	sed -i -e "s/init.sql/init.sql.nekeng/" /usr/local/bin/init.install.nekeng 

COPY	nekeng/nekeng.conf /etc/httpd/conf.d/

COPY	kzkt45/install/kzkt45.sql.tar.gz /root/
COPY	kzkt45/install/kzkt45.tar.gz /root/
ADD	kzkt45/kzkt45/init.install /usr/local/bin/init.install.kzkt45
ADD	kzkt45/kzkt45/init.sql /usr/local/bin/init.sql.kzkt45
RUN	chmod +x /usr/local/bin/init.install.kzkt45 && \
	sed -i -e "s/init.sql/init.sql.kzkt45/" /usr/local/bin/init.install.kzkt45 

COPY	kzkt45/kzkt45.conf /etc/httpd/conf.d/

COPY	ipark45/install/ipark45.sql.tar.gz /root/
COPY	ipark45/install/ipark45.tar.gz /root/
ADD	ipark45/ipark45/init.install /usr/local/bin/init.install.ipark45
ADD	ipark45/ipark45/init.sql /usr/local/bin/init.sql.ipark45
RUN	chmod +x /usr/local/bin/init.install.ipark45 && \
	sed -i -e "s/init.sql/init.sql.ipark45/" /usr/local/bin/init.install.ipark45 

COPY	ipark45/ipark45.conf /etc/httpd/conf.d/

COPY	ite-ng/install/ite-ng.sql.tar.gz /root/
COPY	ite-ng/install/ite-ng.tar.gz /root/
ADD	ite-ng/ite-ng/init.install /usr/local/bin/init.install.ite-ng
ADD	ite-ng/ite-ng/init.sql /usr/local/bin/init.sql.ite-ng
RUN	chmod +x /usr/local/bin/init.install.ite-ng && \
	sed -i -e "s/init.sql/init.sql.ite-ng/" /usr/local/bin/init.install.ite-ng 

ADD	init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

COPY	ite-ng/ite-ng.conf /etc/httpd/conf.d/aite-ng.conf

RUN	sed -i -e "s/'172\.18\.2\.1'\;/'172\.18\.2\.4'\;/" /etc/phpMyAdmin/config.inc.php && \
	sed -i -e "s/\=172\.18\.2\.1/\=172\.18\.2\.4/" /usr/local/bin/init.install.nekeng  && \
	sed -i -e "s/\=172\.18\.2\.1/\=172\.18\.2\.4/" /usr/local/bin/init.install.kzkt45  && \
	sed -i -e "s/\=172\.18\.2\.1/\=172\.18\.2\.4/" /usr/local/bin/init.install.ipark45  && \
	sed -i -e "s/\=172\.18\.2\.1/\=172\.18\.2\.4/" /usr/local/bin/init.install.ite-ng

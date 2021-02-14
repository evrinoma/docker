FROM \
	evrinoma/php74:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.4 web" \
	image="php74.web" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-14"

COPY	nekeng/install/nekeng.sql.tar.gz /root/src/
COPY	nekeng/install/nekeng.tar.gz /root/src/
COPY	nekeng/nekeng/init.install /usr/local/bin/init.install.nekeng
COPY	nekeng/nekeng/init.sql /usr/local/bin/init.sql.nekeng
RUN	chmod +x /usr/local/bin/init.install.nekeng && \
	sed -i -e "s/init.sql/init.sql.nekeng/" /usr/local/bin/init.install.nekeng 

COPY	nekeng/nekeng.conf /etc/httpd/conf.d/

COPY	kzkt45/install/kzkt45.sql.tar.gz /root/src/
COPY	kzkt45/install/kzkt45.tar.gz /root/src/
COPY	kzkt45/kzkt45/init.install /usr/local/bin/init.install.kzkt45
COPY	kzkt45/kzkt45/init.sql /usr/local/bin/init.sql.kzkt45
RUN	chmod +x /usr/local/bin/init.install.kzkt45 && \
	sed -i -e "s/init.sql/init.sql.kzkt45/" /usr/local/bin/init.install.kzkt45 

COPY	kzkt45/kzkt45.conf /etc/httpd/conf.d/

COPY	ipark45/install/ipark45.sql.tar.gz /root/src/
COPY	ipark45/install/ipark45.tar.gz /root/src/
COPY	ipark45/ipark45/init.install /usr/local/bin/init.install.ipark45
COPY	ipark45/ipark45/init.sql /usr/local/bin/init.sql.ipark45
RUN	chmod +x /usr/local/bin/init.install.ipark45 && \
	sed -i -e "s/init.sql/init.sql.ipark45/" /usr/local/bin/init.install.ipark45 

COPY	ipark45/ipark45.conf /etc/httpd/conf.d/

COPY	vargashi45/install/vargashi45.sql.tar.gz /root/src/
COPY	vargashi45/install/vargashi45.tar.gz /root/src/
COPY	vargashi45/vargashi45/init.install /usr/local/bin/init.install.vargashi45
COPY	vargashi45/vargashi45/init.sql /usr/local/bin/init.sql.vargashi45
RUN	chmod +x /usr/local/bin/init.install.vargashi45 && \
	sed -i -e "s/init.sql/init.sql.vargashi45/" /usr/local/bin/init.install.vargashi45

COPY	vargashi45/vargashi45.conf /etc/httpd/conf.d/

COPY	kpsz/install/kpsz.sql.tar.gz /root/src/
COPY	kpsz/install/kpsz.tar.gz /root/src/
COPY	kpsz/kpsz/init.install /usr/local/bin/init.install.kpsz
COPY	kpsz/kpsz/init.sql /usr/local/bin/init.sql.kpsz
RUN	chmod +x /usr/local/bin/init.install.kpsz && \
	sed -i -e "s/init.sql/init.sql.kpsz/" /usr/local/bin/init.install.kpsz

COPY	kpsz/kpsz.conf /etc/httpd/conf.d/

COPY	ite-ng/install/ite-ng.sql.tar.gz /root/src/
COPY	ite-ng/install/ite-ng.tar.gz /root/src/
COPY	ite-ng/ite-ng/init.install /usr/local/bin/init.install.ite-ng
COPY	ite-ng/ite-ng/init.sql /usr/local/bin/init.sql.ite-ng
RUN	chmod +x /usr/local/bin/init.install.ite-ng && \
	sed -i -e "s/init.sql/init.sql.ite-ng/" /usr/local/bin/init.install.ite-ng 

COPY	lazurnoe/lazurnoe.conf /etc/httpd/conf.d/

COPY	lazurnoe/install/lazurnoe.tar.gz /root/src/
COPY	lazurnoe/lazurnoe/init.install /usr/local/bin/init.install.lazurnoe
RUN	chmod +x /usr/local/bin/init.install.lazurnoe

COPY	init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

COPY	ite-ng/ite-ng.conf /etc/httpd/conf.d/aite-ng.conf

#RUN	chmod -R 640 /tmp && \
#	chmod -R 640 /var/tmp

COPY	init.container.part /usr/local/bin
RUN	cat /usr/local/bin/init.container.part >> /usr/local/bin/init.container && \
	rm -rf /usr/local/bin/init.container.part

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi

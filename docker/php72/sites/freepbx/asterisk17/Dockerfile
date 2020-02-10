FROM \
	evrinoma/php72:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma PHP 7.2 asterisk 17.2" \
	image="asterisk 17.2" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-10"

RUN	rm -rf /var/cache/yum && \
	yum remove -y nodejs && \
	yum clean all && \
	curl --silent --location https://rpm.nodesource.com/setup_10.x | bash -  && \
	yum install -y nodejs yarn

RUN cd /root && \
    wget http://downloads.asterisk.org/pub/telephony/asterisk/asterisk-17.2.0.tar.gz && \
    tar zxvf asterisk-17.2.0.tar.gz
RUN cd /root/asterisk* && \
    ./contrib/scripts/install_prereq  install && \
    ./contrib/scripts/get_mp3_source.sh

RUN cd /root/asterisk* && ./configure --with-pjproject-bundled --with-jansson-bundled --with-crypto --with-ssl=ssl --with-srtp
RUN cd /root/asterisk* && make
RUN cd /root/asterisk* && make install
RUN cd /root/asterisk* && make config
RUN cd /root/asterisk* && make samples
RUN cd /root/asterisk* && ldconfig

RUN sed -i 's/ASTARGS=""/ASTARGS="-U asterisk"/g' /usr/sbin/safe_asterisk
RUN useradd -m asterisk
RUN chown asterisk.asterisk /var/run/asterisk && \
    chown -R asterisk.asterisk /etc/asterisk && \
    chown -R asterisk.asterisk /var/{lib,log,spool}/asterisk && \
    chown -R asterisk.asterisk /usr/lib/asterisk
RUN runuser -l asterisk -c /usr/sbin/asterisk


RUN	chmod -R 640 /tmp && \
	chmod -R 640 /var/tmp

RUN	if [ "${MODE}" == "prod" ]; then rm -rf /etc/php.d/xdebug.ini ; fi




EXPOSE 443
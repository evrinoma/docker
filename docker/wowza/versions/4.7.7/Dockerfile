FROM \
	evrinoma/wowza:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma wowza 4.7.7 Image" \
	image="wowza.4.7.7" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-23"

COPY	install/WowzaStreamingEngine-4.7.7-linux-x64-installer.run /opt/wowza/wowza-installer.run
COPY	install/wowza /etc/sysconfig/wowza
COPY	wowzaInstall /usr/local/bin
RUN	sed -i '/###WOWZA_INSERT/r /etc/sysconfig/wowza' /usr/local/bin/wowzaInstall
RUN	chmod 700 /opt/wowza/wowza-installer.run && \
	chmod 700 /usr/local/bin/wowzaInstall
RUN	wowzaInstall

COPY	install/wowza.tar.gz /usr/local/WowzaStreamingEngine
#RUN	init.install





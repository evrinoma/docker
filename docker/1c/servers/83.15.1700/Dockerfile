FROM \
	evrinoma/1c:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.15.1700" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-26"

#1c install
ADD     install/rpm64_8_3_15_1700.tar.gz /opt/WWW/install
WORKDIR	/opt/WWW/install
RUN	rpm -ivh 1C_Enterprise83-common-8.3.15-1700.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-common-nls-8.3.15-1700.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-ws-8.3.15-1700.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-ws-nls-8.3.15-1700.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-server-8.3.15-1700.x86_64.rpm && \
	rpm -ivh 1C_Enterprise83-crs-8.3.15-1700.x86_64.rpm && \
	rm -rf /opt/WWW/install
WORKDIR	/

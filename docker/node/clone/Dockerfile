FROM \
	evrinoma/node:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma node.clone Image" \
	image="node.clone" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-02-07"

ARG	BASE_PATH=''
ARG	BASE_TARGET=''

RUN	echo "BASE_PATH=${BASE_PATH}" > /etc/sysconfig/nhost && \
	echo "BASE_TARGET=${BASE_TARGET}" >> /etc/sysconfig/nhost

RUN groupadd -g 48 apache &&\
    adduser -g apache -u 48 --no-create-home -s /bin/nologin apache &&\
    id apache

ADD init.install /usr/local/bin
RUN	sed -i '/###NODE_INSERT/r /etc/sysconfig/nhost' /usr/local/bin/init.install &&\
    chmod +x /usr/local/bin/init.install &&\
    init.install

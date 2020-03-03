FROM \
	evrinoma/bind:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma bind Image" \
	image="mail bind" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-03-03"

RUN	mv -v /etc/named.conf{,.bak} && \
	rm -rf /etc/named/ && \
	chown -R named.named /var/named

RUN	echo "MODE=${MODE}" >> /etc/sysconfig/bind
RUN	echo "INSTALL_DIR=${INSTALL_DIR}" >> /etc/sysconfig/bind
RUN	sed -i '/###BIND_INSERT/r /etc/sysconfig/bind' /usr/local/bin/init.install && init.install

RUN	if [ "${MODE}" == "master" ]; then ln -s ${INSTALL_DIR}/${MODE}/named.conf /etc/ && \
	ln -s ${INSTALL_DIR}/${MODE}/named.external.zones /etc/ && \
	ln -s ${INSTALL_DIR}/${MODE}/named/ /etc/ ; fi

RUN	if [ "${MODE}" == "slave" ]; then ln -s ${INSTALL_DIR}/${MODE}/named.conf /etc/ && \
	ln -s ${INSTALL_DIR}/${MODE}/named.external.zones /etc/ && \
	ln -s ${INSTALL_DIR}/${MODE}/named.internal.zones /etc/ ; fi

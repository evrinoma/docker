FROM \
	evrinoma/mattermost:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Matermost Image" \
	image="matermost" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-01-25"

RUN	echo "VERSION=${VERSION}" >> /etc/sysconfig/vhost && \
	echo "VHOST=${VHOST}" >> /etc/sysconfig/vhost && \
	echo "database_host=${database_host}" >> /etc/sysconfig/vhost && \
	echo "database_port=${database_port}" >> /etc/sysconfig/vhost && \
	echo "database_name=${database_name}" >> /etc/sysconfig/vhost && \
	echo "database_user=${database_user}" >> /etc/sysconfig/vhost && \
	echo "database_password=${database_password}" >> /etc/sysconfig/vhost
RUN echo ${PKI}
#mattermost
RUN	mkdir -p /opt/WWW/${VERSION} && \
    mkdir -p /opt/WWW/container.ite-ng.ru/project/${VHOST}/${VERSION}/plugins && \
    mkdir -p /opt/WWW/container.ite-ng.ru/project/${VHOST}/${VERSION}/client/plugins && \
    chmod -R 777 /opt/WWW/container.ite-ng.ru/project/${VHOST}/${VERSION} && \
    ln -s /opt/WWW/${VHOST}/bin/mattermost /usr/local/bin

RUN	wget http://releases.mattermost.com/${VERSION}/mattermost-${VERSION}-linux-amd64.tar.gz -O /opt/WWW/${VERSION}/mattermost.tar.gz && \
	tar -xvzf /opt/WWW/${VERSION}/mattermost.tar.gz -C /opt/WWW/

RUN	mv /opt/WWW/${VHOST}/config/config.json{,.bak} && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/config.json /opt/WWW/${VHOST}/config/config.json && \
    mkdir -p /opt/WWW/container.ite-ng.ru/conf/${VHOST}/certs/ && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/certs/ca.key /etc/pki/nginx/mattermost/ca.key && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/certs/ca.pem /etc/pki/nginx/mattermost/ca.pem


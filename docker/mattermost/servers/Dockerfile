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

#mattermost
RUN	mkdir -p /opt/WWW/${VERSION} && \
    ln -s /opt/WWW/${VHOST}/bin/mattermost /usr/local/bin

COPY install/mattermost-${VERSION}-linux-amd64.tar.gz /opt/WWW/${VERSION}/mattermost.tar.gz
RUN ls -la /opt/WWW/${VERSION}
RUN	if [ ! -f "/opt/WWW/${VERSION}/mattermost.tar.gz" ]; then curl -L http://releases.mattermost.com/${VERSION}/mattermost-${VERSION}-linux-amd64.tar.gz --output /opt/WWW/${VERSION}/mattermost.tar.gz ; fi

#RUN	curl -L http://releases.mattermost.com/${VERSION}/mattermost-${VERSION}-linux-amd64.tar.gz --output /opt/WWW/${VERSION}/mattermost.tar.gz && \
RUN    tar -xvzf /opt/WWW/${VERSION}/mattermost.tar.gz -C /opt/WWW/

#   mv /opt/WWW/${VHOST}/config/config.json{,.bak} && \
#    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/config.json /opt/WWW/${VHOST}/config/config.json && \
RUN ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/certs/ca.key /etc/pki/nginx/mattermost/ca.key && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/certs/ca.pem /etc/pki/nginx/mattermost/ca.pem && \
    mv -v /opt/WWW/${VHOST}/config{,.bak} && \
    mv -v /opt/WWW/${VHOST}/client{,.bak} && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/config /opt/WWW/${VHOST}/config && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/plugins /opt/WWW/${VHOST}/plugins && \
    ln -s /opt/WWW/container.ite-ng.ru/conf/${VHOST}/client /opt/WWW/${VHOST}/client


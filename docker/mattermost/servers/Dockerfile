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
RUN	mkdir -p /opt/WWW/${VERSION}
RUN	wget http://releases.mattermost.com/${VERSION}/mattermost-${VERSION}-linux-amd64.tar.gz -O /opt/WWW/${VERSION}/mattermost.tar.gz


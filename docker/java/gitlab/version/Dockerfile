FROM \
	evrinoma/java:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma GitLab Image" \
	image="java.gitlab" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2021-01-12"

ARG	VERSION=''

RUN	yum -y install curl \
	policycoreutils-python \
	postfix \
	which

RUN	touch /etc/profile.d/gitlab.sh && \
	chmod 755 /etc/profile.d/gitlab.sh && \
	echo '#/etc/profile.d Oracle gitlab set' >> /etc/profile.d/gitlab.sh && \
	echo 'export LC_ALL="en_US.UTF-8"' >> /etc/profile.d/gitlab.sh && \
	echo 'export LC_CTYPE="en_US.UTF-8"' >> /etc/profile.d/gitlab.sh

RUN	echo "VERSION=${VERSION}" >> /etc/sysconfig/vhost
RUN	mkdir -p /opt/WWW/${VERSION}
COPY	${VERSION}/install /opt/WWW/${VERSION}/

RUN	if [ ! -f "/opt/WWW/${VERSION}/script.rpm.sh" ]; then curl -L https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh --output /opt/WWW/${VERSION}/script.rpm.sh ; fi
RUN	if [ ! -f "/opt/WWW/${VERSION}/gitlab-runner_amd64.rpm" ]; then curl -L https://gitlab-runner-downloads.s3.amazonaws.com/latest/rpm/gitlab-runner_amd64.rpm --output /opt/WWW/${VERSION}/gitlab-runner_amd64.rpm ; fi
RUN	if [ ! -f "/opt/WWW/${VERSION}/gitlab-ee-${VERSION}-ee.0.el7.x86_64.rpm" ]; then curl -L https://packages.gitlab.com/gitlab/gitlab-ee/packages/el/7/gitlab-ee-${VERSION}-ee.0.el7.x86_64.rpm/download.rpm --output /opt/WWW/${VERSION}/gitlab-ee-${VERSION}-ee.0.el7.x86_64.rpm ; fi

#RUN	wget https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh -P /root && \
#	chmod 700 /root/script.rpm.sh  && \
RUN	chmod 700 /opt/WWW/${VERSION}/script.rpm.sh &&  /opt/WWW/${VERSION}/script.rpm.sh

#RUN	wget --content-disposition https://gitlab-runner-downloads.s3.amazonaws.com/latest/rpm/gitlab-runner_amd64.rpm -P /tmp
#RUN	wget --content-disposition https://packages.gitlab.com/gitlab/gitlab-ee/packages/el/7/gitlab-ee-${VERSION}-ee.0.el7.x86_64.rpm/download.rpm -P /tmp

RUN	rpm -ivh /opt/WWW/${VERSION}/gitlab-runner_amd64.rpm
RUN	rpm -ivh /opt/WWW/${VERSION}/gitlab-ee-${VERSION}-ee.0.el7.x86_64.rpm




FROM \
	evrinoma/java:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma GitLab Image" \
	image="java.gitlab" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-12"

RUN	yum -y install curl \
	policycoreutils-python \
	postfix \
	which

RUN	touch /etc/profile.d/gitlab.sh && \
	chmod 755 /etc/profile.d/gitlab.sh && \
	echo '#/etc/profile.d Oracle gitlab set' >> /etc/profile.d/gitlab.sh && \
	echo 'export LC_ALL="en_US.UTF-8"' >> /etc/profile.d/gitlab.sh && \
	echo 'export LC_CTYPE="en_US.UTF-8"' >> /etc/profile.d/gitlab.sh

RUN	wget https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh -P /root && \
	chmod 700 /root/script.rpm.sh  && \
	/root/script.rpm.sh
    
RUN	wget --content-disposition https://gitlab-runner-downloads.s3.amazonaws.com/latest/rpm/gitlab-runner_amd64.rpm -P /tmp
RUN	wget --content-disposition https://packages.gitlab.com/gitlab/gitlab-ee/packages/el/7/gitlab-ee-12.10.0-ee.0.el7.x86_64.rpm/download.rpm -P /tmp

RUN	rpm -ivh /tmp/gitlab-runner_amd64.rpm && \
	rpm -ivh /tmp/gitlab-ee-12.10.10-ee.0.el7.x86_64.rpm

#EXPOSE 8749 8750 8751


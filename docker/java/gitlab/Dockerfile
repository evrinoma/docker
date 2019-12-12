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
	postfix

RUN	wget https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh -P /root && \
	chmod 700 /root/script.rpm.sh  && \
	/root/script.rpm.sh

RUN	wget --content-disposition https://packages.gitlab.com/gitlab/gitlab-ee/packages/el/7/gitlab-ee-12.5.4-ee.0.el7.x86_64.rpm/download.rpm -P /tmp
RUN	rpm -ivh /tmp/download.rpm
#RUN	yum -y install gitlab-ee

#EXPOSE 8749 8750 8751

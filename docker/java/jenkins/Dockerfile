FROM \
	evrinoma/java:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Jenkins Image" \
	image="java.jenkins" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-12"

RUN	wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo && \
	rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key && \
	yum -y install jenkins

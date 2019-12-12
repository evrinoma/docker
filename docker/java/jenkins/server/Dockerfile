FROM \
	evrinoma/java.jenkins:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Jenkins Image" \
	image="java.jenkins.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-12"

COPY	supervisor.jenkins.conf /etc/supervisord.d/jenkins.conf

COPY	jenkins/init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install


FROM \
	evrinoma/java.gitblit:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma GitLab Image" \
	image="java.gitlab.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-12"

#COPY	supervisor.gitlab.conf /etc/supervisord.d/gitlab.conf
COPY	gitlab/init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

#EXPOSE 8749 8750 8751

FROM \
	evrinoma/java:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Gitblit Image" \
	image="java.gitblit" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-10"

COPY	supervisor.gitblit.conf /etc/supervisord.d/gitblit.conf
COPY	gitblit/init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

EXPOSE 8749 8750 8751

FROM \
	evrinoma/git:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Gitblit Image" \
	image="git.gitblit" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-10"

COPY	gitblit/init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

FROM \
	evrinoma/java.gitlab:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma GitLab Image" \
	image="java.gitlab.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-18"

ONBUILD	ARG	GITLABHOST='git\.ite-ng\.ru'

RUN	sed -i -e "s/gitlab\.example\.com/git\.ite-ng\.ru/" /etc/gitlab/gitlab.rb
RUN	head -n 40 /etc/gitlab/gitlab.rb
COPY	supervisor.gitlab.conf /etc/supervisord.d/gitlab.conf
COPY	gitlab/init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

EXPOSE 80 443 8060 9090 9100 9121 9187 9188 9168 8082 8083 26379 143 5000 5050 8443 8088 465 514 8065 6432 8300 8500

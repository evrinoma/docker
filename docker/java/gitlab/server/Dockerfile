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

ONBUILD
ARG	GITLAB_HOST=''
ARG	SMTP_ADDRESS=''
ARG	SMTP_PORT=''
ARG	SMTP_USER_NAME=''
ARG	SMTP_PASSWORD=''
ARG	SMTP_DOMAIN=''

COPY	install/vhost /etc/sysconfig/vhost

RUN	if [ ! -z "${GITLAB_HOST}" ]; then  echo "GITLAB_HOST=${GITLAB_HOST}" > /etc/sysconfig/vhost ; fi && \
	if [ ! -z "${SMTP_ADDRESS}" ]; then  echo "SMTP_USER_NAME=${SMTP_ADDRESS}" >> /etc/sysconfig/vhost ; fi && \
	if [ ! -z "${SMTP_PORT}" ]; then  echo "SMTP_PASSWORD=${SMTP_PASSWORD}" >> /etc/sysconfig/vhost ; fi && \
	if [ ! -z "${SMTP_USER_NAME}" ]; then  echo "SMTP_USER_NAME=${SMTP_USER_NAME}" >> /etc/sysconfig/vhost ; fi && \
	if [ ! -z "${SMTP_PASSWORD}" ]; then  echo "SMTP_PASSWORD=${SMTP_PASSWORD}" >> /etc/sysconfig/vhost ; fi && \
	if [ ! -z "${SMTP_DOMAIN}" ]; then  echo "SMTP_DOMAIN=${SMTP_DOMAIN}" >> /etc/sysconfig/vhost ; fi

RUN export GITLAB_HOST=$(cat /etc/sysconfig/vhost | grep GITLAB_HOST | sed -r 's/.*=(.*)$/\1/') && \
    export SMTP_ADDRESS=$(cat /etc/sysconfig/vhost | grep SMTP_ADDRESS | sed -r 's/.*=(.*)$/\1/') && \
    export SMTP_PORT=$(cat /etc/sysconfig/vhost | grep SMTP_PORT | sed -r 's/.*=(.*)$/\1/') && \
    export SMTP_USER_NAME=$(cat /etc/sysconfig/vhost | grep SMTP_USER_NAME | sed -r 's/.*=(.*)$/\1/') && \
    export SMTP_PASSWORD=$(cat /etc/sysconfig/vhost | grep SMTP_PASSWORD | sed -r 's/.*=(.*)$/\1/') && \
    export SMTP_DOMAIN=$(cat /etc/sysconfig/vhost | grep SMTP_DOMAIN | sed -r 's/.*=(.*)$/\1/')

RUN	sed -i -e "s/gitlab\.example\.com/${GITLAB_HOST}/" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_enable'\]@gitlab_rails\['smtp_enable'\]@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_authentication'\]@gitlab_rails\['smtp_authentication'\]@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_enable_starttls_auto'\]@gitlab_rails\['smtp_enable_starttls_auto'\]@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_port'\] =.*@gitlab_rails\['smtp_port'\] = ${SMTP_PORT}@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_address'\] =.*@gitlab_rails\['smtp_address'\] = ${SMTP_ADDRESS}@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_user_name'\] =.*@gitlab_rails\['smtp_user_name'\] = ${SMTP_USER_NAME}@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_password'\] =.*@gitlab_rails\['smtp_password'\] = ${SMTP_PASSWORD}@" /etc/gitlab/gitlab.rb && \
    sed -i -e "s@# gitlab_rails\['smtp_domain'\] =.*@gitlab_rails\['smtp_domain'\] = ${SMTP_DOMAIN}@" /etc/gitlab/gitlab.rb

RUN	head -n 40 /etc/gitlab/gitlab.rb
COPY	supervisor.gitlab.conf /etc/supervisord.d/gitlab.conf
COPY	gitlab/init.install /usr/local/bin/init.install
RUN	chmod +x /usr/local/bin/init.install

EXPOSE 80 443 8060 9090 9100 9121 9187 9188 9168 8082 8083 26379 143 5000 5050 8443 8088 465 514 8065 6432 8300 8500

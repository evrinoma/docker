FROM \
	evrinoma/java.gitlab:latest

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
#  44  /opt/gitlab/embedded/bin/runsvdir-start &
#  45  gitlab-ctl reconfigure
#   46  /opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8
#   47  sudo -s bash -c '/opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8'
#   48  sudo - gitlab-psgl bash -c '/opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8'
#   49  su -c '/opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8' gitlab-psgl
#   50  id gitlab*
#   51  cat /etc/shadow
#   52  su -c '/opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8' gitlab-psql
#   53  export LC_ALL="en_US.UTF-8" and export LC_CTYPE="en_US.UTF-8"
#   54  su -c '/opt/gitlab/embedded/bin/initdb -D /var/opt/gitlab/postgresql/data -E UTF8' gitlab-psql
#EXPOSE 8749 8750 8751

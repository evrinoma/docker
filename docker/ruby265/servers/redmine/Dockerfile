FROM \
		evrinoma/ruby265:latest

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma Redmine Image" \
		image="redmine" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2019-12-19"

RUN /bin/bash -l -c 'rvm use --default 2.6.5 && gem install passenger && passenger-install-apache2-module --auto'

COPY	init.container /usr/local/bin/
COPY	redmine/init.install /usr/local/bin
COPY	redmine/init.sql /usr/local/bin
COPY	restartHttpd /usr/local/bin
COPY	redmine.conf /etc/httpd/conf.d/vhost.conf
COPY	mysqlpls.rb /usr/local/bin
RUN	chmod +x /usr/local/bin/init.container && \
    chmod +x /usr/local/bin/init.install && \
    chmod +x /usr/local/bin/restartHttpd && \
	chmod +x /usr/local/bin/init.sql


ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

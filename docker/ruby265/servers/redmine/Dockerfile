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

RUN  gem install passenger

COPY	init.container /usr/local/bin/
COPY	redmine/init.install /usr/local/bin
COPY	redmine/init.sql /usr/local/bin
RUN	chmod 700 /usr/local/bin/init.container && \
    chmod +x /usr/local/bin/init.install && \
	chmod +x /usr/local/bin/init.sql


ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

FROM \
		evrinoma/db.maria:latest

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma Web Maria SQL Image" \
		image="maria.db.public" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2019-12-05"

ADD	install/db.tar.gz /tmp
RUN	mv /var/lib/mysql{,.bak} && \
	cp -r /tmp/db/maria/ /var/lib/mysql && \
	chown mysql.mysql -R /var/lib/mysql && \
	rm -rf /tmp/db && \
	rm -rf /var/lib/mysql.bak/

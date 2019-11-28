FROM \
	evrinoma/postgre.1c:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Postgre SQL Image" \
	image="postgre.1c.10.5.24" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-25"

ADD	install/postgresql_10.5_24.1C_x86_64_rpm.tar.bz2 /opt/WWW/install
ADD	install/postgresql_10.5_24.1C_x86_64_addon_rpm.tar.bz2 /opt/WWW/install

WORKDIR	/opt/WWW/install/postgresql-10.5-24.1C_x86_64_rpm
RUN	rpm -ivh postgresql10-1c-libs-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-contrib-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-server-10.5-24.el7.x86_64.rpm

WORKDIR	/opt/WWW/install/postgresql-10.5-24.1C_x86_64_addon_rpm
RUN	rpm -ivh postgresql10-1c-debuginfo-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-docs-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-pltcl-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-devel-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-test-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-plpython-10.5-24.el7.x86_64.rpm && \
	rpm -ivh postgresql10-1c-plperl-10.5-24.el7.x86_64.rpm

#RUN	groupadd psql && \
#	useradd -g postgres -s /bin/bash -d /home/postgres postgres && \
#	echo 'postgres' |passwd postgres --stdin

RUN	rm -rf /opt/WWW/install
WORKDIR	/

ADD	postgres /usr/local/bin/
RUN	chmod +x /usr/local/bin/postgres

RUN	mv /usr/pgsql-10/bin/postgresql-10-setup{,.bak}
ADD	postgresql-10-setup /usr/pgsql-10/bin
RUN	chmod +x /usr/pgsql-10/bin/postgresql-10-setup

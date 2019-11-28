FROM \
	evrinoma/postgre.1c:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Postgre SQL Image" \
	image="postgre.1c.11.5.1" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-26"

ADD	install/postgresql_11.5_1.1C_x86_64_addon_rpm.tar.bz2 /opt/WWW/install
ADD	install/postgresql_11.5_1.1C_x86_64_rpm.tar.bz2 /opt/WWW/install

WORKDIR	/opt/WWW/install/postgresql-11.5-1.1C_x86_64_rpm

RUN	rpm -ivh postgresql11-1c-libs-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-contrib-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-server-11.5-1.el7.x86_64.rpm
WORKDIR	/opt/WWW/install/postgresql-11.5-1.1C_x86_64_addon_rpm

RUN	rpm -ivh postgresql11-1c-debuginfo-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-docs-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-pltcl-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-devel-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-test-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-plpython-11.5-1.el7.x86_64.rpm && \
	rpm -ivh postgresql11-1c-plperl-11.5-1.el7.x86_64.rpm

RUN	rm -rf /opt/WWW/install
WORKDIR	/

ADD	postgres /usr/local/bin/
RUN	chmod +x /usr/local/bin/postgres

RUN	mv /usr/pgsql-11/bin/postgresql-11-setup{,.bak}
ADD	postgresql-11-setup /usr/pgsql-11/bin
RUN	chmod +x /usr/pgsql-11/bin/postgresql-11-setup

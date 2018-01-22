FROM \
		centos:centos7

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma APP Image" \
		image="Container" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2017-12-30"

ENV	container docker

RUN	(cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == systemd-tmpfiles-setup.service ] || rm -f $i; done); \
	rm -f /lib/systemd/system/multi-user.target.wants/*;\
	rm -f /etc/systemd/system/*.wants/*;\
	rm -f /lib/systemd/system/local-fs.target.wants/*; \
	rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
	rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
	rm -f /lib/systemd/system/basic.target.wants/*;\
	rm -f /lib/systemd/system/anaconda.target.wants/*;
	VOLUME [ "/sys/fs/cgroup" ]
CMD	["/usr/sbin/init"]

RUN	yum -y install mc && \
	yum -y install git && \
	yum -y install wget;

RUN	yum -y install mariadb-server mariadb && \
	yum -y install php php-mysql php-gd && \
	yum clean all;

RUN	systemctl enable mariadb.service

RUN	mysql_install_db --user=mysql --ldata=/var/lib/mysql/
RUN	sed -i '/\[mysqld\]/aport=3306' /etc/my.cnf
RUN	chown -R mysql.mysql /var/lib/mysql/

EXPOSE 3306

CMD	["/usr/bin/mysqld_safe"]


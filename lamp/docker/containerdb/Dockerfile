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
RUN	sed -i '/\[mysqld\]/aport=3306' /etc/my.cnf && \
	sed -i '/\[mysqld\]/askip-external-locking' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/amax_allowed_packet=256M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/amax_allowed_packet=4096M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/atable_cache=4096' /etc/my.cnf && \
	sed -i '/\[mysqld\]/asort_buffer_size=128M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/aread_buffer_size=64M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/aread_rnd_buffer_size=64M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/amyisam_sort_buffer_size=512M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/athread_cache_size=8' /etc/my.cnf && \
	sed -i '/\[mysqld\]/aquery_cache_size=1024M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/aquery_cache_limit=2048M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/ajoin_buffer_size=128M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/atmp_table_size=128M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/amax_heap_table_size=256M' /etc/my.cnf && \
	sed -i '/\[mysqld\]/amax_connection=100' /etc/my.cnf && \
	sed -i '/\[mysqld\]/athread_concurrency=12' /etc/my.cnf && \
	sed -i '/\[mysqld\]/amax_binlog_size=512M' /etc/my.cnf
#	sed -i '/\[mysqld\]/askip-innodb' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aslow_query_log=1' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aslow_query_log_file=\/var\/log\/mysqld\.slow\.log' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/along_query_time=5' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/alog-queries-not-using-indexes' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/alog-bin=mysql-bin' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aexpire_logs_days=3' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aserver-id=1' /etc/my.cnf

#	sed -ire 's/max_allowed_packet.*=.*/max_allowed_packet = /g' /etc/mysql/my.cnf

RUN	chown -R mysql.mysql /var/lib/mysql/

EXPOSE 3306

CMD	["/usr/bin/mysqld_safe"]


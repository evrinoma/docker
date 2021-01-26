FROM \
		centos:centos7

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma Maria SQL Image" \
		image="maria.db" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2019-05-21"

#ENV	container docker

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

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	supervisor \
	openssh-server;

RUN	yum -y install \
	telnet;

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";

#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;

RUN	yum -y install mariadb-server mariadb && \
	yum -y install php php-mysql php-gd && \
	yum clean all;

COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

RUN	systemctl enable mariadb.service

RUN	echo 'HOSTNAME=maria.db' >> /etc/sysconfig/network && \
	echo '172.18.2.1 maria.db' >> /etc/hosts

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
	sed -i '/\[mysqld\]/amax_binlog_size=512M' /etc/my.cnf &&\
	sed -i -e 's/socket\=.*/socket\=\/var\/run\/mariadb\/mysql.sock/' /etc/my.cnf
#	sed -i '/\[mysqld\]/askip-innodb' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aslow_query_log=1' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aslow_query_log_file=\/var\/log\/mysqld\.slow\.log' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/along_query_time=5' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/alog-queries-not-using-indexes' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/alog-bin=mysql-bin' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aexpire_logs_days=3' /etc/my.cnf && \
#	sed -i '/\[mysqld\]/aserver-id=1' /etc/my.cnf && \
#	sed -ire 's/max_allowed_packet.*=.*/max_allowed_packet = /g' /etc/mysql/my.cnf

RUN	mkdir -p /var/run/mariadb && \
	chown -R mysql.mysql /var/run/mariadb/ && \
	echo '[client]' >> /etc/my.cnf && \
	echo 'socket=/var/run/mariadb/mysql.sock' >> /etc/my.cnf

RUN	chown -R mysql.mysql /var/lib/mysql/

RUN	mysql_install_db --force --user=mysql --ldata=/var/lib/mysql/
COPY	create.user /usr/local/bin/
COPY	init.container /usr/local/bin/
RUN	chmod 700 /usr/local/bin/init.container && \
	chmod 700 /usr/local/bin/create.user

CMD ["/usr/bin/supervisord"]

EXPOSE 22 3306

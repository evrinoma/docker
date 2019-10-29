FROM \
	centos:centos7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma sftp Image" \
	image="sftp" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-10-07"

RUN	echo "nameserver 172.20.1.5" > /etc/resolv.conf && \
	echo "nameserver 8.8.8.8" >> /etc/resolv.conf

RUN	yum -y install mc wget && \
	yum -y install iproute && \
	yum -y update;

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install gcc \
	make;

RUN	yum -y install git \
	supervisor \
	openssh-server;

RUN	yum -y install \
	telnet \
	patch.x86_64;

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 0631  | passwd --stdin root;

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

#sftpdg - localuser group for sftp access 
#sftp_linux - ldapuser group for sftp access 
RUN	groupadd sftpg
#	useradd -g sftpg seenisftp && \
#	echo 1234 | passwd --stdin seenisftp;

RUN	yum -y install authconfig sssd sssd-tools sssd-ldap openldap-clients
COPY	sssd.conf /etc/sssd/
COPY	krb5.conf /etc/
COPY	ssh-user-perm /usr/local/bin/
COPY	update-cache /usr/local/bin/
RUN	authconfig --enablesssd --enablesssdauth --enablemkhomedir --updateall && \
	echo 'session     [success=ok default=1]    pam_exec.so /usr/local/bin/ssh-user-perm' >> /etc/pam.d/password-auth-ac && \
	chmod 600 /etc/sssd/sssd.conf && \
	chmod 700 /usr/local/bin/ssh-user-perm && \
	chmod 700 /usr/local/bin/update-cache && \
	sed -i -e "s/#AuthorizedKeysCommand .*/AuthorizedKeysCommand \/usr\/bin\/sss_ssh_authorizedkeys /" /etc/ssh/sshd_config && \
	sed -i -e "s/#AuthorizedKeysCommandUser .*/AuthorizedKeysCommandUser root/" /etc/ssh/sshd_config && \
	sed -i -e "s/HostKey \/etc\/ssh\/ssh_host_ecdsa_key/#&/" /etc/ssh/sshd_config && \
	sed -i -e "s/HostKey \/etc\/ssh\/ssh_host_ed25519_key/#&/" /etc/ssh/sshd_config && \
	echo 'AllowGroups root sftpg sftp_linux' >> /etc/ssh/sshd_config && \
#	echo 'AllowUsers root' >> /etc/ssh/sshd_config && \
	echo 'Match Group sftp_linux User "*,!root"' >> /etc/ssh/sshd_config && \
	echo '	ChrootDirectory /home/%u' >> /etc/ssh/sshd_config && \
	echo '	ForceCommand internal-sftp' >> /etc/ssh/sshd_config && \
	echo '	X11Forwarding no' >> /etc/ssh/sshd_config && \
	echo '' >> /etc/ssh/sshd_config && \
	echo 'Match Group sftpg User "*,!root"' >> /etc/ssh/sshd_config && \
	echo '	ChrootDirectory /home/%u' >> /etc/ssh/sshd_config && \
	echo '	ForceCommand internal-sftp' >> /etc/ssh/sshd_config && \
	echo '	X11Forwarding no' >> /etc/ssh/sshd_config

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22

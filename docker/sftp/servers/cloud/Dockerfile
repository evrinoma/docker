FROM \
	evrinoma/sftp:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma sftp cloud Image" \
	image="sftp cloud" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-01-31"

COPY	ssh-user-perm /usr/local/bin/
RUN	chmod 700 /usr/local/bin/ssh-user-perm
RUN	echo 'AllowGroups root sftpg sftp_linux' >> /etc/ssh/sshd_config && \
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

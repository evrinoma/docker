FROM \
	evrinoma/1c:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-07-17"
	
RUN	echo "[program:1C_Enterprise83-server]" >> /etc/supervisord.conf && \
	echo "command=/etc/init.d/srv1cv83 start" >> /etc/supervisord.conf
	
FROM \
	evrinoma/ubuntu.vnc:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Wine Image" \
	image="wine" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-06-06"

RUN	apt-get install -y cabextract
RUN	dpkg --add-architecture i386 &&  apt update
RUN	apt install -y wine32
RUN	wget https://raw.githubusercontent.com/Winetricks/winetricks/master/src/winetricks -P /root && chmod +x /root/winetricks
RUN	export WINEARCH=win32
#RUN	sh /root/winetricks ie8  --unattended
RUN	touch /usr/local/bin/ie && echo 'wine /root/.wine/drive_c/Program\ Files/Internet\ Explorer/iexplore.exe' > /usr/local/bin/ie && chmod +x /usr/local/bin/ie

EXPOSE	9001
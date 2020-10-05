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
RUN	ln -s /opt/WWW/container.ite-ng.ru/conf/wine/ /root/.wine

RUN	apt install -y libatkmm-1.6-1v5 libcairomm-1.0-1v5 libglibmm-2.4-1v5 libgtkmm-3.0-1v5 libpangomm-1.4-1v5 libpython2.7 libsigc++-2.0-0v5 libssh-4 libvsqlitepp3v5 libproj-dev proj-bin
RUN	wget https://dev.mysql.com/get/mysql-apt-config_0.8.15-1_all.deb -P /root
RUN	apt install -y /root/mysql-apt-config_0.8.15-1_all.deb && apt update && apt install -y mysql-workbench-community
RUN	ln -s /opt/WWW/container.ite-ng.ru/conf/workbench/ /root/.mysql


EXPOSE	9001
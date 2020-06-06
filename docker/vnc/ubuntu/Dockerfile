FROM \
	ubuntu:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma VNC Image" \
	image="vnc" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-06-06"

ENV	DEBIAN_FRONTEND=noninteractive
RUN	apt-get update && apt-get install -y mc wget iproute2

RUN	apt-get install -y gcc make

RUN	apt-get install -y git supervisor openssh-server

RUN	apt-get install -y telnet

#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";

#vnc
RUN	apt-get install -y tigervnc-standalone-server tigervnc-xorg-extension tigervnc-viewer
#RUN	apt-get install -y ubuntu-gnome-desktop
RUN	apt-get install -y ubuntu-desktop
RUN	apt-get install -y gnome-panel gnome-settings-daemon metacity nautilus gnome-terminal

#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisor/conf.d
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisor/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisor/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

RUN	export LANG=en_US.UTF-8
COPY    vnc/ /root/
#copy terminal settings
#COPY xfce4 /root/.config/xfce4
#RUN	sed -i '/xfce-polkit/d' /etc/xdg/autostart/xfce-polkit.desktop

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 5901

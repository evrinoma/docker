FROM centos:7

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma OpenMCU Image" \
	image="open mcu" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-09-29"


# fail if build with libvpx > 1.5

ENV NASM_VER="2.13.01" \
 MCU_CONF_OPTS="--enable-static --disable-v4l --disable-v4l2" \
 FFMPEG_VER="4.3" \
 LIBVPX_VER="1.5.0"

RUN mkdir -p /tmp/build \
 && cd /tmp/build \
 && yum install -y wget bzip2 which freetype-devel freetype \
 kernel-devel gcc gcc-c++ make patch git flex bison autoconf automake pkgconfig \
 rpm-build libtool libtool-ltdl-devel openssl-devel \
 && wget http://www.nasm.us/pub/nasm/releasebuilds/${NASM_VER}/nasm-${NASM_VER}.tar.xz \
 && tar -xvf ./nasm-${NASM_VER}.tar.xz \
 && cd nasm-${NASM_VER} \
 && ./configure && make && make install && cd .. \
 && git clone https://code.videolan.org/videolan/x264.git ./x264 \
 && cd ./x264 \
 && ./configure --enable-shared && make && make install && cd .. \
 && wget https://github.com/webmproject/libvpx/archive/v${LIBVPX_VER}.tar.gz \
 && tar -xzf v${LIBVPX_VER}.tar.gz \
 && cd libvpx-${LIBVPX_VER} \
 && ./configure --enable-shared && make && make install && cd .. \
 && wget http://ffmpeg.org/releases/ffmpeg-${FFMPEG_VER}.tar.gz \
 && tar xvfz ./ffmpeg-${FFMPEG_VER}.tar.gz \
 && cd ./ffmpeg-${FFMPEG_VER} \
 && ./configure --enable-libx264 --enable-libvpx --enable-gpl --disable-static --enable-shared \
 && make -j 3 && make install && cd .. \
 && git clone git://github.com/muggot/openmcu.git ./openmcu-ru \
 && cd ./openmcu-ru \
 && ./autogen.sh && ./configure $MCU_CONF_OPTS && make -j 3 && make install \
 && rm -rf /tmp/* \
 && yum remove -y wget bzip2 which git freetype-devel flex bison autoconf automake pkgconfig \
 rpm-build libtool libtool-ltdl-devel openssl-devel \
 && yum clean all || echo "not all clean" \
 && rm -rf /var/cache/yum

RUN	rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && \
	rpm -Uvh https://mirror.webtatic.com/yum/el7/webtatic-release.rpm;

RUN	yum -y install git \
	mc \
	openssh-server \
	supervisor;
#git
RUN	git config --global user.email "evrinoma@gmail.com" && \
	git config --global user.name "Nikolay Nikolaev";
#ssh
RUN	ssh-keygen -f /etc/ssh/ssh_host_rsa_key -N '' -t rsa && \
	ssh-keygen -f /etc/ssh/ssh_host_dsa_key -N '' -t dsa && \
	echo 1234 | passwd --stdin root;
#history
RUN	ln -s /opt/WWW/container.ite-ng.ru/history /root/.bash_history

#supervisor
COPY	supervisor.conf /etc/supervisord.d/supervisor.conf
RUN	sed -i -e "s/^nodaemon=false/nodaemon=true/" /etc/supervisord.conf && \
	sed -i -e "s/^files\ =\ supervisord\.d\/\*\.ini/files\ =\ supervisord\.d\/\*\.conf/" /etc/supervisord.conf

#mount
RUN	mkdir -p /opt/WWW/container.ite-ng.ru/

#CMD ["/opt/openmcu-ru/bin/openmcu-ru", "-x"]

ENTRYPOINT ["/usr/bin/supervisord","-n"]
EXPOSE 22 5060 5061 1420 1554 1720

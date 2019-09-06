FROM \
	evrinoma/vnc:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Primaver 15.1" \
	image="primavera.15.1" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-09-05"

RUN	mkdir -v /opt/Oracle/ && \
	mkdir -v /opt/Oracle/CSIntgSrv && \
	mkdir -v /opt/Oracle/DOMAIN && \
	mkdir -v /opt/Oracle/INVENTORY && \
	mkdir -v /opt/Oracle/JAVA && \
	mkdir -v /opt/Oracle/LOG && \
	mkdir -v /opt/Oracle/P6 && \
	mkdir -v /opt/Oracle/Scheduler && \
	mkdir -v /opt/Oracle/WEBLOGIC && \
	mkdir -v /opt/Oracle/Work

RUN	chmod -R 777 /opt/Oracle && chown -R oracle.oracle /opt/Oracle

RUN	touch /etc/profile.d/primavera.sh && \
	chmod 755 /etc/profile.d/primavera.sh && \
	echo '#/etc/profile.d Oracle primavera set' >> /etc/profile.d/primavera.sh && \
	echo 'JAVA_HOME=/opt/Oracle/JAVA' >> /etc/profile.d/primavera.sh && \
	echo 'export PATH=$JAVA_HOME/bin:$PATH' >> /etc/profile.d/primavera.sh

RUN	yum install xdpyinfo -y

#install
COPY	install/Inventory /opt/WWW/install/Inventory
COPY	install/JDK /opt/WWW/install/JDK
COPY	install/Weblogic /opt/WWW/install/Weblogic
RUN	ls -la /opt/WWW/install/ && \
	ls -la /opt/WWW/install/Inventory && \
	ls -la /opt/WWW/install/Weblogic
RUN	cp /opt/WWW/install/Inventory/oraInst.loc /opt/Oracle/INVENTORY

WORKDIR /opt/WWW/install

RUN	tar -xvf /opt/WWW/install/JDK/jdk-12.0.2_linux-x64_bin.tar.gz -C /opt/Oracle/JAVA/ && mv -v /opt/Oracle/JAVA/jdk-12.0.2/* /opt/Oracle/JAVA/ && rm -rf /opt/Oracle/JAVA/jdk-12.0.2/
ENV	JAVA_HOME /opt/WWW/install/JDK/
RUN	export JAVA_HOME
RUN	su - oracle -c "java -jar /opt/WWW/install/Weblogic/fmw_12.2.1.3.0_wls.jar -silent -responseFile /opt/WWW/install/Weblogic/weblogic.resp -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"

#yum install binutils compat-libcap1 compat-libstdc++-33 compat-libstdc++-33.i686 glibc glibc.i686 glibc-devel glibc-devel.i686 ksh libaio libaio.i686 libaio-devel libaio-devel.i686 libX11 libX11.i686 libXau libXau.i686 libXi libXi.i686 libXtst libXtst.i686 libgcc libgcc.i686 libstdc++ libstdc++.i686 libstdc++-devel libstdc++-devel.i686 libxcb libxcb.i686 make nfs-utils net-tools smartmontools sysstat unixODBC unixODBC-devel -y

#	rm -rf /opt/WWW/install
WORKDIR /

EXPOSE 7001

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
	ln -s /opt/WWW/container.ite-ng.ru/logs/primavera /opt/Oracle/LOG && \
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
RUN	su - oracle -c "java -jar /opt/WWW/install/Weblogic/fmw_12.2.1.4.0_wls.jar -silent -responseFile /opt/WWW/install/Weblogic/weblogic.resp -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"

COPY	install/primavera /etc/sysconfig/primavera
COPY	primavera/startWLS /usr/local/bin
COPY	primavera/stopWLS /usr/local/bin
COPY	primavera/P6 /usr/local/bin
COPY	primaveraInstall /usr/local/bin
COPY	primavera/primavera.12.2.1.4.tar.gz /opt/Oracle/DOMAIN/
COPY	primavera/domain-registry.xml /opt/Oracle/WEBLOGIC/

RUN	chmod +x /usr/local/bin/startWLS && \
	chmod +x /usr/local/bin/stopWLS && \
	chmod +x /usr/local/bin/P6 && \
	chmod +x /usr/local/bin/primaveraInstall

RUN	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/startWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/stopWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/P6

RUN	mkdir -p /opt/Oracle/DOMAIN/user_projects/domains && \
	ln -s /opt/WWW/container.ite-ng.ru/projects/primavera /opt/Oracle/DOMAIN/user_projects/domains/primavera

WORKDIR /

EXPOSE 7001

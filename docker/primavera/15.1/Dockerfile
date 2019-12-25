FROM \
	evrinoma/vnc:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Primaver 15.1" \
	image="primavera.15.1" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-25"

RUN	mkdir -v /opt/Oracle/ && \
	mkdir -v /opt/Oracle/CSIntgSrv && \
	mkdir -v /opt/Oracle/DOMAIN && \
	mkdir -v /opt/Oracle/INVENTORY && \
	mkdir -v /opt/Oracle/JAVA && \
	ln -s /opt/WWW/container.ite-ng.ru/logs/primavera /opt/Oracle/LOG && \
	mkdir -v /opt/Oracle/P6 && \
	mkdir -v /opt/Oracle/Scheduler

RUN	chmod -R 777 /opt/Oracle && chown -R oracle.oracle /opt/Oracle

RUN	touch /etc/profile.d/primavera.sh && \
	chmod 755 /etc/profile.d/primavera.sh && \
	echo '#/etc/profile.d Oracle primavera set' >> /etc/profile.d/primavera.sh && \
	echo 'JAVA_HOME=/opt/Oracle/JAVA' >> /etc/profile.d/primavera.sh && \
	echo 'OPATCH_HOME=/opt/Oracle/WEBLOGIC/OPatch' >> /etc/profile.d/primavera.sh && \
	echo 'export PATH=$OPATCH_HOME:$JAVA_HOME/bin:$PATH' >> /etc/profile.d/primavera.sh

RUN	yum install xdpyinfo unzip -y

#install
COPY	install/Inventory /opt/WWW/install/Inventory
COPY	install/Java /opt/WWW/install/Java
COPY	install/WebLogic /opt/WWW/install/WebLogic
COPY	install/primavera /etc/sysconfig/primavera
COPY	primavera/startWLS /usr/local/bin
COPY	primavera/stopWLS /usr/local/bin
COPY	primavera/P6 /usr/local/bin
COPY	primaveraInstall /usr/local/bin
COPY	primavera/primavera.12.1.3.0.0.tar.gz /opt/WWW/install/

RUN	cp /opt/WWW/install/Inventory/oraInst.loc /opt/Oracle/INVENTORY && \
    chown -R oracle.oracle /opt/Oracle && \
    chown -R oracle.oracle /opt/WWW/install

WORKDIR /opt/WWW/install

RUN	tar -xvf /opt/WWW/install/Java/jdk-7u80-linux-x64.tar.gz -C /opt/Oracle/JAVA/ && mv -v /opt/Oracle/JAVA/jdk1.7.0_80/* /opt/Oracle/JAVA/ && rm -rf /opt/Oracle/JAVA/jdk1.7.0_80/
ENV	JAVA_HOME /opt/WWW/install/Java/
RUN	export JAVA_HOME

RUN	su - oracle -c "java -jar /opt/WWW/install/WebLogic/fmw_12.1.3.0.0_wls.jar -silent -responseFile /opt/WWW/install/WebLogic/weblogic.resp -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"

RUN	chmod +x /usr/local/bin/startWLS && \
	chmod +x /usr/local/bin/stopWLS && \
	chmod +x /usr/local/bin/P6 && \
	chmod +x /usr/local/bin/primaveraInstall

RUN	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/startWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/stopWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/P6 && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/primaveraInstall

RUN	mkdir -p /opt/Oracle/DOMAIN/user_projects/domains && \
	ln -s /opt/WWW/container.ite-ng.ru/projects/primavera /opt/Oracle/DOMAIN/user_projects/domains/primavera

RUN unzip -d /opt/WWW/install/WebLogic/Updates/ /opt/WWW/install/WebLogic/Updates/p26519417_121300_Generic.zip && \
    chown -R oracle.oracle /opt/WWW/install
RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/26519417/ && opatch apply"

WORKDIR /

EXPOSE 7001

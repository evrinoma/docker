FROM \
	evrinoma/centos.vnc:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Primaver 15.1.1" \
	image="primavera.15.1.1" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-12-25"

RUN	groupadd oracle && \
	useradd -g oracle -s /bin/bash -d /home/oracle oracle && \
	echo 'oracle' |passwd oracle --stdin

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
COPY	install/Primavera16/linux /opt/WWW/install/Primavera16/linux
COPY	primavera/startWLS /usr/local/bin
COPY	primavera/stopWLS /usr/local/bin
COPY	primavera/P6 /usr/local/bin
COPY	primaveraInstall /usr/local/bin
COPY	init.container /usr/local/bin/
COPY	primavera.conf /etc/supervisord.d/primavera.conf

RUN	cp /opt/WWW/install/Inventory/oraInst.loc /opt/Oracle/INVENTORY && \
    chown -R oracle.oracle /opt/Oracle && \
    chown -R oracle.oracle /opt/WWW/install

WORKDIR /opt/WWW/install

RUN	tar -xvf /opt/WWW/install/Java/jdk-8u231-linux-x64.tar.gz -C /opt/Oracle/JAVA/ && mv -v /opt/Oracle/JAVA/jdk1.8.0_231/* /opt/Oracle/JAVA/ && rm -rf /opt/Oracle/JAVA/jdk1.8.0_231/
ENV	JAVA_HOME /opt/WWW/install/Java/
RUN	export JAVA_HOME

#HOW TO RUN MANUAL
#xhost +localhost && export DISPLAY=localhost:1.0 && java -jar /opt/WWW/install/WebLogic/fmw_12.2.1.4.0_wls.jar  -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc
RUN	su - oracle -c "java -jar /opt/WWW/install/WebLogic/fmw_12.2.1.4.0_wls.jar -silent -responseFile /opt/WWW/install/WebLogic/weblogic.resp -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"

RUN	chmod +x /usr/local/bin/startWLS && \
	chmod +x /usr/local/bin/stopWLS && \
	chmod +x /usr/local/bin/P6 && \
	chmod +x /usr/local/bin/primaveraInstall && \
	chmod +x /usr/local/bin/init.container

RUN	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/startWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/stopWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/P6 && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/primaveraInstall

RUN	mkdir -p /opt/Oracle/DOMAIN/user_projects/domains
#	ln -s /opt/WWW/container.ite-ng.ru/projects/primavera /opt/Oracle/DOMAIN/user_projects/domains/primavera

RUN mv /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/basicWLSDomain.py{,.bak}
ADD basicWLSDomain.py /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/

#CREATE DOMAIN
RUN sed -i 's@###WEBLOGIC_SRW@'$(sed 's@\$ORACLE_ROOT@'"$(cat /etc/sysconfig/primavera | grep 'ORACLE_ROOT='|sed -r 's/.*=(.*)$/\1/')"'@' /etc/sysconfig/primavera | grep 'WEBLOGIC_SRW' | sed -r 's/.*=(.*)$/\1/')'@' /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/basicWLSDomain.py && \
	sed -i 's@###DOMAIN_HOME@'$(sed 's@\$ORACLE_ROOT@'"$(cat /etc/sysconfig/primavera | grep 'ORACLE_ROOT='|sed -r 's/.*=(.*)$/\1/')"'@' /etc/sysconfig/primavera | grep 'DOMAIN_HOME' | sed -r 's/.*=(.*)$/\1/')'@' /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/basicWLSDomain.py && \
	sed -i 's@###PRIMAVERA_USERNAME@'$(cat /etc/sysconfig/primavera | grep 'PRIMAVERA_USERNAME' | sed -r 's/.*=(.*)$/\1/')'@' /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/basicWLSDomain.py && \
	sed -i 's@###PRIMAVERA_PASSWORD@'$(cat /etc/sysconfig/primavera | grep 'PRIMAVERA_PASSWORD' | sed -r 's/.*=(.*)$/\1/')'@' /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/basicWLSDomain.py

RUN /opt/Oracle/WEBLOGIC/wlserver/common/bin/wlst.sh /opt/Oracle/WEBLOGIC/wlserver/common/templates/scripts/wlst/basicWLSDomain.py

#RUN unzip -d /opt/WWW/install/WebLogic/Updates/ /opt/WWW/install/WebLogic/Updates/p26519417_121300_Generic.zip && \
#    chown -R oracle.oracle /opt/WWW/install
#RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/26519417/ && printf 'y' | opatch apply"

WORKDIR /

EXPOSE 7001

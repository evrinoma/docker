FROM \
	evrinoma/centos.vnc:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma Primaver 18.8" \
	image="primavera.18.8" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2020-03-05"

RUN	groupadd oracle && \
	useradd -g oracle -s /bin/bash -d /home/oracle oracle && \
	echo 'oracle' |passwd oracle --stdin && \
	echo 'export DISPLAY=localhost:1.0' >> /home/oracle/.bashrc

RUN echo 'xhost +localhost' >> /root/.bashrc

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
COPY	install/Java/jdk-8u181-linux-x64.tar.gz /opt/WWW/install/Java/
COPY	install/WebLogic /opt/WWW/install/WebLogic
COPY	install/primavera /etc/sysconfig/primavera
COPY	install/Primavera18/linux/update /opt/WWW/install/Primavera18/linux/update
COPY	install/Primavera18/linux/api /opt/Oracle/P6/api
COPY	install/Primavera18/linux/database /opt/Oracle/P6/database
COPY	install/Primavera18/linux/p6 /opt/Oracle/P6/p6
COPY	install/Primavera18/linux/p6procloudconnect /opt/Oracle/P6/p6procloudconnect
COPY	install/Primavera18/linux/tmws /opt/Oracle/P6/tmws
COPY	install/Primavera18/linux/ws /opt/Oracle/P6/ws
COPY	primavera/startWLS /usr/local/bin
COPY	primavera/stopWLS /usr/local/bin
COPY	primavera/P6 /usr/local/bin
COPY	primaveraInstall /usr/local/bin
COPY	primaveraDB /usr/local/bin
COPY	init.container /usr/local/bin/
COPY	primavera.conf /etc/supervisord.d/primavera.conf

RUN	cp /opt/WWW/install/Inventory/oraInst.loc /opt/Oracle/INVENTORY && \
    chown -R oracle.oracle /opt/Oracle && \
    chown -R oracle.oracle /opt/WWW/install

WORKDIR /opt/WWW/install

RUN	tar -xvf /opt/WWW/install/Java/jdk-8u181-linux-x64.tar.gz -C /opt/Oracle/JAVA/ && mv -v /opt/Oracle/JAVA/jdk1.8.0_181/* /opt/Oracle/JAVA/ && rm -rf /opt/Oracle/JAVA/jdk1.8.0_181/
ENV	JAVA_HOME /opt/WWW/install/Java/
RUN	export JAVA_HOME

#HOW TO RUN MANUAL
#xhost +localhost && export DISPLAY=localhost:1.0 && java -jar /opt/WWW/install/WebLogic/fmw_12.2.1.3.0_wls.jar  -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc
RUN	su - oracle -c "java -jar /opt/WWW/install/WebLogic/fmw_12.2.1.3.0_wls.jar -silent -responseFile /opt/WWW/install/WebLogic/weblogic.resp -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"

RUN unzip -d /opt/WWW/install/WebLogic/Updates/2019-04/ /opt/WWW/install/WebLogic/Updates/2019-04/p28186730_139400_Generic.zip && \
	unzip -d /opt/WWW/install/WebLogic/Updates/2019-04/ /opt/WWW/install/WebLogic/Updates/2019-04/p29016089_122130_Generic && \
	unzip -d /opt/WWW/install/WebLogic/Updates/2019-10/ /opt/WWW/install/WebLogic/Updates/2019-10/p28186730_139400_Generic.zip && \
	unzip -d /opt/WWW/install/WebLogic/Updates/2019-10/ /opt/WWW/install/WebLogic/Updates/2019-10/p30386660_122130_Generic.zip && \
	unzip -d /opt/WWW/install/WebLogic/Updates/2020-01/ /opt/WWW/install/WebLogic/Updates/2020-01/p28186730_139422_Generic.zip && \
	unzip -d /opt/WWW/install/WebLogic/Updates/2020-01/ /opt/WWW/install/WebLogic/Updates/2020-01/p30675853_122130_Generic.zip && \
    chown -R oracle.oracle /opt/WWW/install

RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/2019-04/6880880 && java -jar opatch_generic.jar -silent oracle_home=/opt/Oracle/WEBLOGIC/ -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"
RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/2019-04/29016089 && printf 'y' | opatch apply -oop"
#all ready exist
#RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/2019-10/6880880 && java -jar opatch_generic.jar -silent oracle_home=/opt/Oracle/WEBLOGIC/ -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"
RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/2019-10/30386660 && printf 'y' | opatch apply -oop"
RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/2020-01/6880880 && java -jar opatch_generic.jar -silent oracle_home=/opt/Oracle/WEBLOGIC/ -invPtrLoc /opt/Oracle/INVENTORY/oraInst.loc"
RUN	su - oracle -c "cd /opt/WWW/install/WebLogic/Updates/2020-01/30675853 && printf 'y' | opatch apply -oop"

RUN	chmod +x /usr/local/bin/startWLS && \
	chmod +x /usr/local/bin/stopWLS && \
	chmod +x /usr/local/bin/P6 && \
	chmod +x /usr/local/bin/primaveraInstall && \
	chmod +x /usr/local/bin/primaveraDB && \
	chmod +x /usr/local/bin/init.container

RUN	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/startWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/stopWLS && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/P6 && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/primaveraInstall && \
	sed -i '/###PRIMAVERA_INSERT/r /etc/sysconfig/primavera' /usr/local/bin/primaveraDB

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

RUN cd /opt/WWW/install/Primavera18/linux/update && unzip Updates.zip && \
    cd /opt/WWW/install/Primavera18/linux/update/Updates && unzip Primavera\ P6\ EPPM\ 18.8.17.0.zip  && \
    cd /opt/WWW/install/Primavera18/linux/update/Updates && unzip P6EPPM-188170.zip && \
    chown -R oracle.oracle /opt/WWW/install/Primavera18/linux/update/ && \
    chmod +x /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/PatchUtility/linux64/install/runInstaller && \
    chmod +x /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/PatchUtility/linux64/install/runInstaller.sh && \
    chmod +x /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/PatchUtility/linux64/install/unzip && \
    sed -i '/if \[ ! -s $JAVA_HOME*/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/PatchUtility/linux64/install/runInstaller.sh

RUN cp -rf /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/API/* /opt/Oracle/P6/api/ && \
    cp -rf /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/WEB/* /opt/Oracle/P6/p6/ && \
    cp -rf /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/p6procloudconnect/* /opt/Oracle/P6/p6procloudconnect/ && \
    cp -rf /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/TM/* /opt/Oracle/P6/tmws/ && \
    cp -rf /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/WS/* /opt/Oracle/P6/ws/ && \
    cd /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/database/ && chmod +x *.sh && chmod g+x *.sh  && \
    cp -rf /opt/WWW/install/Primavera18/linux/update/Updates/P6EPPM-188170/P6EPPM-188170/database/* /opt/Oracle/P6/database/

RUN sed -i 's@MY_INTG_HOME@\/opt\/Oracle\/P6\/api@' /opt/Oracle/P6/api/dbconfig.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/api/dbconfig.sh && \
    sed -i 's@MY_INTG_HOME@\/opt\/Oracle\/P6\/api@' /opt/Oracle/P6/api/admin.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/api/admin.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/charconversion.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/databaselogins.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/dbsetup.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/installp6keystore.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/migrate.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/rundataloader.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/runscript.sh && \
    sed -i '/if \[ -z "$JAVA_HOME" \] \; then/i JAVA_HOME=\/opt\/Oracle\/JAVA' /opt/Oracle/P6/database/validate.sh && \
    sed -i 's@MY_PV_HOME@\/opt\/Oracle\/P6\/p6@' /opt/Oracle/P6/p6/adminpv.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/p6/adminpv.sh && \
    sed -i 's@MY_PV_HOME@\/opt\/Oracle\/P6\/p6@' /opt/Oracle/P6/p6/dbconfigpv.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/p6/dbconfigpv.sh && \
    sed -i 's@MY_P6PRO_HOME@\/opt\/Oracle\/P6\/p6procloudconnect@' /opt/Oracle/P6/p6procloudconnect/admin_p6pro.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/p6procloudconnect/admin_p6pro.sh && \
    sed -i 's@MY_P6PRO_HOME@\/opt\/Oracle\/P6\/p6procloudconnect@' /opt/Oracle/P6/p6procloudconnect/dbconfig_p6pro.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/p6procloudconnect/dbconfig_p6pro.sh && \
    sed -i 's@MY_INTG_HOME@\/opt\/Oracle\/P6\/api@' /opt/Oracle/P6/ws/bin/dbconfig.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/ws/bin/dbconfig.sh && \
    sed -i 's@MY_INTG_HOME@\/opt\/Oracle\/P6\/api@' /opt/Oracle/P6/ws/bin/admin.sh && sed -i 's@MY_JAVA_HOME@\/opt\/Oracle\/JAVA@' /opt/Oracle/P6/ws/bin/admin.sh

WORKDIR /


EXPOSE 7001 9099

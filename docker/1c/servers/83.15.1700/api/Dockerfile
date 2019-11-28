FROM \
	evrinoma/1c.83.15.1700:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma 1C Image" \
	image="1c.83.15.1700.api" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-11-26"

#1c configs
COPY	deploy /etc/httpd/deploy

RUN	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir upp2012 -dir /opt/WWW/1c.ite-ng.ru/upp2012/ -connstr "Srvr=172.20.1.26;Ref=upp2012;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_upp2012.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir testzp  -dir /opt/WWW/1c.ite-ng.ru/testzp/  -connstr "Srvr=172.20.1.26;Ref=testzp;"  -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_testzp.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir doc2017 -dir /opt/WWW/1c.ite-ng.ru/doc2017/ -connstr "Srvr=172.20.1.26;Ref=doc2017;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_doc2017.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir upp_nek -dir /opt/WWW/1c.ite-ng.ru/upp_nek/ -connstr "Srvr=172.20.1.26;Ref=upp_nek;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_upp_nek.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir doc_nek2017 -dir /opt/WWW/1c.ite-ng.ru/doc_nek2017/ -connstr "Srvr=172.20.1.26;Ref=doc_nek2017;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_doc_nek2017.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir upp_kzkt -dir /opt/WWW/1c.ite-ng.ru/upp_kzkt/ -connstr "Srvr=172.20.1.26;Ref=upp_kzkt;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_upp_kzkt.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir ERP_oper_test -dir /opt/WWW/1c.ite-ng.ru/ERP_oper_test/ -connstr "Srvr=172.20.1.26;Ref=ERP_oper_test;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_ERP_oper_test.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir doc_oper_test -dir /opt/WWW/1c.ite-ng.ru/doc_oper_test/ -connstr "Srvr=172.20.1.26;Ref=doc_oper_test;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_doc_oper_test.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir erp_oper -dir /opt/WWW/1c.ite-ng.ru/erp_oper/ -connstr "Srvr=172.20.1.26;Ref=erp_oper;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_erp_oper.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir erp_nek2019 -dir /opt/WWW/1c.ite-ng.ru/erp_nek2019/ -connstr "Srvr=172.20.1.26;Ref=erp_nek2019;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_erp_nek2019.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir bp_kzet -dir /opt/WWW/1c.ite-ng.ru/bp_kzet/ -connstr "Srvr=172.20.1.26;Ref=bp_kzet;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_bp_kzet.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir zup_kzet -dir /opt/WWW/1c.ite-ng.ru/zup_kzet/ -connstr "Srvr=172.20.1.26;Ref=zup_kzet;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_zup_kzet.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache24 -wsdir zup_nek -dir /opt/WWW/1c.ite-ng.ru/zup_nek/ -connstr "Srvr=172.20.1.26;Ref=zup_nek;" -confPath /etc/httpd/conf.d/1c.conf -descriptor /etc/httpd/deploy/default_zup_nek.vrd && \
	/opt/1C/v8.3/x86_64/webinst -apache22 -wsdir bp_nek -dir /opt/WWW/app.ite-ng.ru/1c/bp_nek/ -connstr "Srvr=172.20.1.94;Ref=bp_nek;" -confPath /etc/httpd/dump.d/1c.conf -descriptor 1c/default_bp_nek.vrd

RUN	chown -R apache.apache /opt/WWW/1c.ite-ng.ru

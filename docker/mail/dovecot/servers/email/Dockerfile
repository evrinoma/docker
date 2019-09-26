FROM \
	evrinoma/dovecot:latest

MAINTAINER \
	evrinoma@gmail.com

LABEL \
	name="evrinoma email Image" \
	image="email.postfix.deploy" \
	vendor="evrinoma@gmail.com" \
	license="MIT" \
	build-date="2019-09-18"

#postfix configs
COPY	postfix/certs /etc/postfix/certs
COPY	postfix/main.cf  /etc/postfix
COPY	postfix/master.cf /etc/postfix
COPY	postfix/mysql /etc/postfix/mysql

RUN	rm -fr /etc/dovecot/
COPY	dovecot /etc/dovecot
COPY	init.openssl /usr/local/bin/init.openssl
RUN	chmod +x /usr/local/bin/init.openssl

RUN	cd /etc/postfix/certs/certs && make ca && cp /etc/postfix/certs/certs/ca.pem /etc/dovecot/ca.pem && \
	openssl rsa -in /etc/postfix/certs/certs/ca.key -out /etc/postfix/certs/certs/ca.rsa.key -passin 'pass:rjk,fcf2008' && \
	cp /etc/postfix/certs/certs/ca.rsa.key /etc/dovecot/ca.key && \
	ln -s /tmp/auth.log /var/log/dovecot/ && \
	mkdir -p /opt/WWW/container.ite-ng.ru/conf/mail/dovecot/servers/email/certs/
#RUN	cp /etc/dovecot/ca.pem /opt/WWW/container.ite-ng.ru/conf/mail/dovecot/servers/email/certs/ && cp /etc/dovecot/ca.key /opt/WWW/container.ite-ng.ru/conf/mail/dovecot/servers/email/certs/

EXPOSE 25 110 143 993 995
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

RUN	ln -s /tmp/auth.log /var/log/dovecot/

EXPOSE 25 110 143 993 995
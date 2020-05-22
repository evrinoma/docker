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

RUN	sed -i '1s/^/\n/' /etc/supervisord.d/supervisor.conf && \
	sed -i '1s/^/command=chown -R vmail.vmail \/etc\/dovecot\/log\/\n/' /etc/supervisord.d/supervisor.conf && \
	sed -i '1s/^/[program:permissions]\n/' /etc/supervisord.d/supervisor.conf

RUN	sed -i '1s/^/\n/' /etc/supervisord.d/supervisor.conf && \
	sed -i '1s/^/command=init.openssl\n/' /etc/supervisord.d/supervisor.conf && \
	sed -i '1s/^/[program:init.openssl]\n/' /etc/supervisord.d/supervisor.conf

RUN	touch /var/spool/cron/apache && chown root.root /var/spool/cron/apache  && chmod 400 /var/spool/cron/apache
#RUN	chmod -R 640 /tmp && \
#	chmod -R 640 /var/tmp
RUN	chmod -R 640 /var/tmp

EXPOSE 25 110 143 993 995
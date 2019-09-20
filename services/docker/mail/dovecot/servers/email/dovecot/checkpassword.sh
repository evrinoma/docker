#!/bin/bash
# Example Dovecot checkpassword script that may be used as both passdb or userdb.
#
# Originally written by Nikolay Vizovitin, 2013.

# Assumes authentication DB is in /etc/dovecot/users, each line has '<user>:<password>' format.
# Implementation guidelines at http://wiki2.dovecot.org/AuthDatabase/CheckPassword

# The first and only argument is path to checkpassword-reply binary.
# It should be executed at the end if authentication succeeds.
CHECKPASSWORD_REPLY_BINARY="$1"

# User and password will be supplied on file descriptor 3.
INPUT_FD=3

# Messages to stderr will end up in mail log (prefixed with "dovecot: auth: Error:")
LOG=/opt/etc/dovecot/logs/auth.log

# Error return codes.
ERR_PERMFAIL=1
ERR_NOUSER=3
ERR_TEMPFAIL=111

# Read input data. Password may be empty if not available (i.e. if doing credentials lookup).
read -d $'\x0' -r -u $INPUT_FD USER
read -d $'\x0' -r -u $INPUT_FD PASS
read -d $'\x0' -r -u $INPUT_FD IP

check=$(/usr/bin/php /opt/etc/dovecot/checker.php --mail=$USER --pass=$PASS --ip=$IP)

if [ -n '$check' ]; then
    read -ra arr <<< "$check"
    HOME="${arr[0]}"
    USER="${arr[1]}"
    PASS="${arr[2]}"
    AUTH="${arr[3]}"
    LOGIN="${arr[4]}"

	export USER
	export HOME
	export password="{PLAIN}$PASS"
	export EXTRA="password vmail vmail"

	echo $USER $PASS $HOME $AUTH $LOGIN $IP >> $LOG

	exec $CHECKPASSWORD_REPLY_BINARY
	exit 0;
fi

echo $USER $PASS $IP >> $LOG

exit $ERR_TEMPFAIL

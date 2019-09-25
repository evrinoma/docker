#! /bin/bash

#LOGS="/opt/etc/squid/RULES/logbuild/logs"
FULL="/tmp/full"
LOGS="/tmp/logs"
OUT="/tmp/result"
DIF="/*"
PATH=$LOGS$DIF
FOUND=0

if [ ${#1} -eq 0 ] 
then 
/bin/echo "Please run as follow ./access.sh ALL"
/bin/echo "           or        ./access.sh username"
/bin/echo "           or        ./access.sh IP"
exit 0
fi


if ! [ -d "$LOGS" ] ; then 
	/bin/mkdir -v $LOGS
fi

/bin/cp -v /opt/etc/squid/logs/access* $LOGS

if [ ${#PATH} -eq 0 ] 
then 
echo "path NOT found"
    return 0
    exit 0
fi

for filename in $PATH
do
/bin/echo "${filename##*/}" | /bin/grep -q ".gz"
if [ $? -eq $FOUND ] ; then
/bin/gunzip $filename
fi
done

echo "" >> $FULL

for filename in $PATH
do
/bin/cat $filename >> $FULL
done

/bin/rm -rfv $LOGS

if ! [ $1 = "ALL" ] ;  then
	/bin/cat $FULL | /bin/grep $1 > $OUT
else
/bin/mv -v $FULL $OUT
fi

/bin/rm -rfv $FULL

/bin/echo "All results will be found in $OUT"
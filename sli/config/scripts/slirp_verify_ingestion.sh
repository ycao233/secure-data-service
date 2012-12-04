#!/bin/bash
#
# This script gets logs from SLIRP
#
#set -x

if [ $# -ne 1 ];
then
  echo "Usage: scripts/slirp_verify_ingestion FILE (run from the config/ directory)"
  exit 1
fi

FILE=$1

echo "******************************************************************************"
echo "**  Verifying SLIRP ingestion with $FILE at `date`"
echo "******************************************************************************"

#
# Get the script
#
unzip -o -q -d /tmp $FILE jsExpected.js

#
# Run it
#
MISMATCHES=`mongo d36f43474916ad310100c9711f21b65bd8231cc6 /tmp/jsExpected.js | grep Mismatch`
if [ -z "$MISMATCHES" ] ; then
  echo "All entity numbers are correct."
else
  echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
  echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
  echo "ENTITY MISMATCHES !!!!!!!!!!!!!!"
  echo "$MISMATCHES"
  echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
  echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
fi

exit



echo "Slow query logs, staging..."
mongo --quiet $ISDB/ingestion_batch_job --eval "db.system.profile.find().forEach(function(x){printjson(x);})" > $NAME/slow_ingestion_batch_job.log
echo "ingestion_batch_job: `scripts/analyze_slow_query_log.sh $NAME/slow_ingestion_batch_job.log`" >> $NAME/slow_summary

for i in $PRIMARIES;
do
  echo "Slow query logs, ${i}/sli..."
  mongo --quiet $i/sli --eval "db.system.profile.find().forEach(function(x){printjson(x);})" > $NAME/slow_${i}_sli.log
  echo "$i/sli: `scripts/analyze_slow_query_log.sh $NAME/slow_${i}_sli.log`" >> $NAME/slow_summary
  echo "Slow query logs, $i/Hyrule..."
  mongo --quiet $i/d36f43474916ad310100c9711f21b65bd8231cc6 --eval "db.system.profile.find().forEach(function(x){printjson(x);})" > $NAME/slow_${i}_hyrule.log
  echo "$i/hyrule: `scripts/analyze_slow_query_log.sh $NAME/slow_${i}_hyrule.log`" >> $NAME/slow_summary
  echo "Slow query logs, $i/02f7abaa9764db2fa3c1ad852247cd4ff06b2c0a..."
  mongo --quiet $i/02f7abaa9764db2fa3c1ad852247cd4ff06b2c0a --eval "db.system.profile.find().forEach(function(x){printjson(x);})" > $NAME/slow_${i}_tenant2.log
  echo "$i/tenant2: `scripts/analyze_slow_query_log.sh $NAME/slow_${i}_tenant2.log`" >> $NAME/slow_summary
  echo "Slow query logs, $i/ff501cb38db19529bc3eb7fd5759f3844626fdf6..."
  mongo --quiet $i/ff501cb38db19529bc3eb7fd5759f3844626fdf6 --eval "db.system.profile.find().forEach(function(x){printjson(x);})" > $NAME/slow_${i}_tenant3.log
  echo "$i/tenant3: `scripts/analyze_slow_query_log.sh $NAME/slow_${i}_tenant3.log`" >> $NAME/slow_summary
done

#
# Copy ingestion logs
#
echo "Copy logs..."
cp -f /opt/logs/ingestion.log /opt/lz/inbound/Hyrule-NYC/job* $NAME
if [ -z "`grep 'Zip file detected' $NAME/ingestion.log`" ] ; then
  # get previous day's log 
  PREV=`ls -t /opt/logs/ingestion-* | head -n 1`
  cat $PREV $NAME/ingestion.log > $NAME/tmp
  mv $NAME/tmp $NAME/ingestion.log
fi
scripts/parse_ingestion_log.sh $NAME/ingestion.log > $NAME/ingestion_summary

#
# Crerate zip file
#
echo "Create zip..."
zip -qr $NAME.zip $NAME

#
# Display contents of zip
#
echo "ZIP file..."
ls -lht $NAME.zip

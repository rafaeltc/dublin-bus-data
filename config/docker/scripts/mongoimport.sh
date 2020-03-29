#! /bin/bash

echo 'importing data from dataset' $1 'into colletion' $2
mongoimport --host mongodb --type csv \
--db transport --collection $2 --drop \
--file=/dataset/$1 \
--columnsHaveTypes \
--parseGrace=skipRow \
  --fields="timestamp.auto(),lineId.int32(),direction.int32(),journeyPatternId.string(),timeframe.date(2006-01-02),vehicleJourneyId.int32(),operator.string(),congestion.auto(),long.decimal(),lat.decimal(),delay.decimal(),blockId.int32(),vehicleId.int32(),stopId.auto(),atStop.boolean()"
#! /bin/bash
mongoimport --host mongodb --type csv \
--db transport --collection $2 --drop \
--file=/dataset/$1 \
--fields="Timestamp,LineId,Direction,JouneyPatternId,Timeframe,VehicleJourneyId,Operator,Congestion,Long,Lat,Delay,BlockId,VehicleId,StopId,AtStop"

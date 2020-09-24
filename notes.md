# dublin-bus-data
Web service that exposes Vehicle (Bus), Fleet (Operator) and Activity (Stop) data, for a given time frame.

## Setup

* install docker
* docker exec -it my-mongo-container ./mongo-seed/import.sh
* docker cp siri.20121130.csv my-mongo-container:/mongo-seed/

## Importing dataset

* docker exec -it mongodbx scripts/mongoimport.sh siri.20130131.csv dublin

docker pull mongo

Start a mongo server instance
$ docker run --name some-mongo -d mongo:tag

https://www.docker.com/sites/default/files/d8/2019-09/docker-cheat-sheet.pdf
https://hub.docker.com/_/mongo
https://github.com/wsargent/docker-cheat-sheet
https://devhints.io/docker-compose
https://medium.com/faun/managing-mongodb-on-docker-with-docker-compose-26bf8a0bbae3
https://gist.github.com/bradtraversy/f407d642bdc3b31681bc7e56d95485b6

## Running 

## 
$ docker-compose up -d 

##
$ docker-compose ls

### Login to your container by using container names
$ docker exec -it <container-name> bash

## Connecting
mongodb://YourUsername:YourPasswordHere@127.0.0.1:27017/your-database-name
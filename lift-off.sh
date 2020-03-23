#! /bin/bash
echo "booting up services..."
docker-compose -f ./config/docker/docker-compose.yml up -d

echo "loading dataset..."
docker exec -it mongodbx ./scripts/mongoimport.sh siri.20130131.csv dublin

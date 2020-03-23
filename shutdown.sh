#! /bin/bash
echo "shutting down services..."
docker-compose -f ./config/docker/docker-compose.yml down

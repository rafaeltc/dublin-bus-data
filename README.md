# dublin-bus-data
Web service that exposes Vehicle (Bus), Fleet (Operator) and Activity (Stop) data, for a given time frame.

## Requirements

* docker

## Setup

### Data sets

The data sets should be placed in the config/docker/dataset folder.

## Running

To launch the container running MongoDB with pre-imported data, run the `lift-off.sh` script, alternatively simply run `docker-compose up` to boot up the services.
To tear down the service, run the `shutdown.sh` script.


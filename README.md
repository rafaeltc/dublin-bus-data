# dublin-bus-data
This application is a microservice that exposes Dublin bus data, namely: Vehicle, Operator and Activity (Stops, Trace) data, for a given time frame.

## Requirements

* Docker
* Kotlin
* Gradle

## Setup

### Data sets

The dataset files can be obtained from the [Dublin Bus GPS Sample Data website](https://data.gov.ie/dataset/dublin-bus-gps-sample-data-from-dublin-city-council-insight-project) and should be placed in the _config/docker/dataset_ folder. 

### Running the app

To run the application you will need to:
1. launch the database service by running the `lift-off.sh <dataset>` script, where _<dataset>_ is the data csv file to load into the database.
2. launch the REST service by running `./gradlew bootRun`. The service will then be available at http://localhost:7777/api/datapoints.

### Stopping the app

To tear down the database service run the `shutdown.sh` script and to stop the rest service simply kill the process.

### Sample Requests

Sample requests can be found in the *Postman collection* at _src/test/postman/DublinBusData.postman_collection.json_.
Additional information on the API will be available at [Swagger UI](http://localhost:7777/swagger-ui.html).

## Documentation

The API can be accessed at:
* [API Docs](http://localhost:7777/v3/api-docs/)
* [API Docs (yaml)](http://localhost:7777/v3/api-docs.yml)
* [Swagger UI](http://localhost:7777/swagger-ui.html)

## TODO
- [x] Add instructions on how to install and/or access to the database 
- [x] Add data loader script
- [x] Add instructions on how to launch the services
- [x] Documentation and/or examples on how to use the API
- [x] Unit testing
- [] Handle high throughput in terms of number of requests
- [] Add support for pagination in requests (Pageable)
- [] Add improved request filtering options
- [] Git actions: Run Tests
- [] Dockerize app
- [] Restrict database access to specific user(s)
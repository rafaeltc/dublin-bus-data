package com.rafaeltc.dublinbusdata.web

import com.rafaeltc.dublinbusdata.model.GPSTrace
import com.rafaeltc.dublinbusdata.services.DataPointRepository
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging
import org.springframework.core.env.Environment
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.stereotype.Component

@Component
class VertXServer(private val repository: DataPointRepository, private val env: Environment) : AbstractVerticle() {

    private val logger = KotlinLogging.logger {}

    override fun start(fut:Future<Void>) {

        // Set up routes
        var router = Router.router(vertx)
        router.get("/api/datapoints").handler(this::getAll)
        router.get("/api/datapoints/vehicles").handler(this::getStoppedVehicles)
        router.get("/api/datapoints/vehicles/:vehicleId").handler(this::getVehicleGPSTrace)
        router.get("/api/datapoints/operators").handler(this::getOperators)
        router.get("/api/datapoints/vehicles/operators/:operator").handler(this::getVehicleIds)

        router.route("/").handler { routingContext ->
            routingContext.response()
                    .putHeader("content-type", "text/html")
                    .end("<h1>This is the Vert.X endpoint!</h1>")
        }

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(env.getProperty("server.vertx.port")?.toInt() ?: 7070  )

    }

    private fun getAll(routingContext: RoutingContext) {
        // Write the HTTP response
        // The response is in JSON using the utf-8 encoding
        // We returns the list of bottles
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(repository.findAll()))
    }

    private fun getStoppedVehicles(routingContext: RoutingContext) {
        with(routingContext.request()) {
            val from = getParam("from").toLong()
            val to = getParam("to").toLong()
            val atStop = getParam("atStop").toBoolean()
            val vehicleIds = getParam("vehicleIds").split(",").map { it.toInt() }

            routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(
                    repository.findStoppedVehicles(from, to, vehicleIds, atStop, Sort.by(Direction.ASC, "vehicleId"))
                            .also { logger.debug { "Query returned ${it.size} results"}}
                            .distinctBy { it.vehicleId }
                            .map { it.vehicleId }
                ))
        }
    }

    private fun getVehicleGPSTrace(routingContext: RoutingContext) {
        with(routingContext.request()) {
            val from = getParam("from").toLong()
            val to = getParam("to").toLong()
            val vehicleId = getParam("vehicleId").toInt()

            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(
                        repository.findVehicleTrace(from, to, vehicleId, Sort.by(Direction.ASC, "timestamp"))
                                .also { logger.debug { "Query returned ${it.size} results"}}
                                .map { GPSTrace(it.timestamp, it.long, it.lat) }
                    ))
        }
    }

    private fun getOperators(routingContext: RoutingContext) {

        with(routingContext.request()) {
            val from = getParam("from").toLong()
            val to = getParam("to").toLong()

            routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(
                    repository.findOperatorsByTimestamp(from, to,Sort.by(Direction.ASC, "operator"))
                            .distinctBy { it.operator }.map { data -> data.operator }
                            .also { logger.debug { "Query returned ${it.size} results"}}
                ))
        }
    }

    private fun getVehicleIds(routingContext: RoutingContext) {

        with(routingContext.request()) {
            val from = getParam("from").toLong()
            val to = getParam("to").toLong()
            val operator = getParam("operator")

            routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(
                    repository.findVehiclesByOperator(from, to, operator, Sort.by(Direction.ASC, "vehicleId"))
                        .distinctBy { it.vehicleId }.map { data -> data.vehicleId }
                        .also { logger.debug { "Query return $it.size results" } }
                ))
        }
    }
}
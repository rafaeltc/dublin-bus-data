package com.rafaeltc.dublinbusdata.web

import com.rafaeltc.dublinbusdata.model.GPSTrace
import com.rafaeltc.dublinbusdata.services.DataPointRepository
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/datapoints") //controller root path
class DataPointController(private val repository: DataPointRepository) {

    private val logger = KotlinLogging.logger {}

    /**
     * Retrieves all datapoints in the database
     * @return ResponseEntity<Map<String, String>>
     */
    @GetMapping
    fun getAll() : ResponseEntity<Map<String, String>> {
        repository.findAll().also {
            return ResponseEntity.ok(it.associate { data -> data.id to data.toString()})
        }
    }

    /**
     * Returns the list of operators given a timeframe
     * @param from Long lower limit of the search timeframe
     * @param to Long upper limit of the search timeframe
     * @param sort Direction sorting direction of the results
     * @return ResponseEntity<List<String>>
     */
    @GetMapping("/operators")
    fun getOperators(@RequestParam from:Long,
                            @RequestParam to:Long,
                            @RequestParam(required = false, defaultValue = "ASC") sort:Sort.Direction) : ResponseEntity<List<String>> {
        repository.findOperatorsByTimestamp(from, to, Sort.by(sort, "operator"))
                .also {
                    logger.debug { "Query returned ${it.size} results" }
                    return ResponseEntity.ok(it.distinctBy { it.operator }.map { data -> data.operator })
                }
    }

    /**
     * Returns the list of vehicle ids given an operator and a timeframe
     * @param operator String identifier of the operator
     * @param from Long lower limit of the search timeframe
     * @param to Long upper limit of the search timeframe
     * @param sort Direction sorting direction of the results
     * @return ResponseEntity<List<String>>
     */
    @GetMapping("/vehicles/operators/{operator}")
    fun getVehicleIds(@RequestParam from:Long,
                      @RequestParam to:Long,
                      @PathVariable operator: String,
                      @RequestParam(required = false, defaultValue = "ASC") sort:Sort.Direction ) : ResponseEntity<List<String>> {

        //repository.findAll(PageRequest.of(0,3, Sort.Direction.DESC, "")).get()
        repository.findVehiclesByOperator(from, to, operator, Sort.by(sort, "vehicleId"))
                .also {
                    logger.debug { "Query returned ${it.size} results" }
                    return ResponseEntity.ok(it.distinctBy { it.vehicleId }.map { data -> data.vehicleId}) }
    }

    /**
     * Returns the stopped/running vehicles given a list of vehicle ids, the stopped status and a timeframe
     * @param vehicleIds List<Int> list of vehicle ids to evaluate
     * @param atStop Boolean true to retrieve the stopped vehicles, false to retrieve the running vehicles
     * @param from Long lower limit of the search timeframe
     * @param to Long upper limit of the search timeframe
     * @param sort Direction sorting direction of the results
     * @return ResponseEntity<List<String>>
     */
    @GetMapping("/vehicles")
    fun getStoppedVehicles(@RequestParam from:Long,
                           @RequestParam to:Long,
                           @RequestParam vehicleIds: List<Int>,
                           @RequestParam atStop: Boolean,
                           @RequestParam(required = false, defaultValue = "ASC") sort:Sort.Direction): ResponseEntity<List<String>> {

        repository.findStoppedVehicles(from, to, vehicleIds, atStop, Sort.by(sort, "vehicleId"))
                .also {
                    logger.debug { "Query returned ${it.size} results" }
                    return ResponseEntity.ok(it.distinctBy { it.vehicleId }.map { it.vehicleId })
                }
    }

    /**
     * Retrieves the GPS trace for a given vehicle during the requested timeframe
     * @param vehicleId Int the identifier of the vehicle
     * @param from Long lower limit of the search timeframe
     * @param to Long upper limit of the search timeframe
     * @param sort Direction sorting direction of the results
     * @return ResponseEntity<List<GPSTrace>>
     */
    @GetMapping("/vehicles/{vehicleId}")
    fun getVehicleGPSTrace(
            @RequestParam from:Long,
            @RequestParam to:Long,
            @PathVariable vehicleId:Int,
            @RequestParam(required = false, defaultValue = "ASC") sort:Sort.Direction): ResponseEntity<List<GPSTrace>> {

        repository.findVehicleTrace(from, to, vehicleId, Sort.by(sort, "timestamp"))
                .also {
                    logger.debug { "Query returned ${it.size} results" }
                    return ResponseEntity.ok(it.map { GPSTrace(it.timestamp, it.long, it.lat) })
                }
    }

}
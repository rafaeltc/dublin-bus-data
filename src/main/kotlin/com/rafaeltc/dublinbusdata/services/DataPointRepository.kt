package com.rafaeltc.dublinbusdata.services

import com.rafaeltc.dublinbusdata.model.DataPoint
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.scheduling.annotation.Async
import java.util.concurrent.Future

//https://www.baeldung.com/spring-data-mongodb-reactive
interface DataPointRepository:MongoRepository<DataPoint,String>{

    @Async
    @Query(value = "{timestamp: {'\$gte':?0, '\$lte':?1}}")
    fun findOperatorsByTimestamp(from:Long, to:Long, sort: Sort): Future<List<DataPoint>>

    @Async
    @Query(value = "{timestamp: {'\$gte':?0, '\$lte':?1}, operator: ?2}")
    fun findVehiclesByOperator(from: Long, to: Long, operator: String, sort: Sort):Future<List<DataPoint>>

    @Async
    @Query(value = "{timestamp: {'\$gte':?0, '\$lte':?1}, vehicleId: ?2}")
    fun findVehicleTrace(from: Long, to: Long, vehicleId: Int, sort: Sort):Future<List<DataPoint>>

    @Async
    @Query(value = "{timestamp: {'\$gte':?0, '\$lte':?1}, vehicleId: {'\$in':[?2]}, atStop:?3 }")
    fun findStoppedVehicles(from: Long, to: Long, vehicleId: List<Int>, atStop: Boolean, sort: Sort):Future<List<DataPoint>>

   }
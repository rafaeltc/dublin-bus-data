package com.rafaeltc.dublinbusdata.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "dublin") data class DataPoint(
        @Id @JsonIgnore val id: String,
        val timestamp: Long,
        val operator: String,
        val vehicleId: String,
        val lineId: Int,
        val direction: Int,
        val journeyPatternId: String,
        val long: Float,
        val lat: Float,
        val atStop: Boolean
        // Congestion
        // Delay
        // BlockId
        // StopId
)

data class GPSTrace( val timestamp: Long, val long: Float, val lat: Float )




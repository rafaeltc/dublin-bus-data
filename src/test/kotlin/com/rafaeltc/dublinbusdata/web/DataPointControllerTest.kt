package com.rafaeltc.dublinbusdata.web

import com.ninjasquad.springmockk.MockkBean
import com.rafaeltc.dublinbusdata.model.DataPoint
import com.rafaeltc.dublinbusdata.services.DataPointRepository
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(DataPointController::class)
internal class DataPointControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var repository: DataPointRepository

    @Test
    fun `should retrieve all datapoints`() {

        every { repository.findAll() } returns listOf<DataPoint>()

        mockMvc.perform(get("/api/datapoints"))
                .andExpect(status().isOk)
                .andDo(print())

        verify { repository.findAll() }
    }

    @Test
    fun `should retrieve all operators given a timeframe`() {

        val from = 1353888001000000
        val to = 1353888003000000

        every { repository.findOperatorsByTimestamp(from, to, any()) } returns listOf()

        mockMvc.perform(get("/api/datapoints/operators")
                        .param("from","$from")
                        .param("to","$to"))
                .andExpect(status().isOk)
                .andDo(print())

        verify { repository.findOperatorsByTimestamp(from, to, any()) }
    }

    @Test
    fun `should retrieve all vehicles given an operator and timeframe`() {
        val from = 1353888001000000
        val to = 1353888003000000
        val operator = "CD"

        every { repository.findVehiclesByOperator(from, to, operator, any()) } returns listOf()

        mockMvc.perform(get("/api/datapoints/vehicles/operators/$operator")
                        .param("from","$from")
                        .param("to","$to"))
                .andExpect(status().isOk)
                .andDo(print())

        verify { repository.findVehiclesByOperator(from, to, operator, any()) }
    }

    @Test
    fun `should retrive all GPS entries given a timeframe`() {
        val from = 1353888001000000
        val to = 1353888003000000
        val vehicleId = 33413

        every { repository.findVehicleTrace(from, to, vehicleId, any()) } returns listOf()

        mockMvc.perform(get("/api/datapoints/vehicles/$vehicleId")
                        .param("from","$from")
                        .param("to","$to"))
                .andExpect(status().isOk)
                .andDo(print())

        verify { repository.findVehicleTrace(from, to, vehicleId, any()) }
    }

    @Test
    fun `get stopped vehicles given timeframe`() {
        val from = 1353888001000000
        val to = 1353888003000000
        val vehicleIdList = listOf(38004,36022,33486)
        val atStop = true;

        every { repository.findStoppedVehicles(from, to, vehicleIdList, atStop, any()) } returns listOf()

        mockMvc.perform(get("/api/datapoints/vehicles/")
                        .param("from","$from")
                        .param("to","$to")
                        .param("vehicleIds", vehicleIdList.joinToString( separator="," ))
                        .param("atStop", atStop.toString()))
                .andExpect(status().isOk)
                .andDo(print())

        verify { repository.findStoppedVehicles(from, to, vehicleIdList, atStop, any()) }
    }
}
package com.rafaeltc.dublinbusdata

import com.rafaeltc.dublinbusdata.web.VertXServer
import io.vertx.core.Vertx
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct


@SpringBootApplication
class DublinbusdataApplication(private val vertXServer: VertXServer) {
	@PostConstruct
	fun deployVerticle() {
		Vertx.vertx().deployVerticle(vertXServer)
	}
}

fun main(args: Array<String>) {
	runApplication<DublinbusdataApplication>(*args)
}




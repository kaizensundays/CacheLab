package com.kaizensundays.eta.cache

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kaizensundays.eta.context.Logger
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

/**
 * Created: Saturday 9/30/2023, 7:28 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
abstract class AbstractWebSocketHandler : WebSocketHandler {

    protected val logger = Logger(javaClass)

    val jsonConverter = JsonMapper.builder()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .addModule(KotlinModule.Builder().build())
        .build()

    private val outbound = Sinks.many().multicast().directBestEffort<ByteArray>()

    abstract fun handle(msg: ByteArray, outbound: Sinks.Many<ByteArray>)

    private fun handle(message: WebSocketMessage) {
        try {
            val data = message.payload
            val msg = ByteArray(data.readableByteCount())
            data.read(msg)
            handle(msg, outbound)
        } catch (e: Throwable) {
            logger.error("", e)
            val result = jsonConverter.writeValueAsString(Response(1, e.message ?: "?", 0))
            outbound.tryEmitNext(result.toByteArray())
        }
    }

    override fun handle(session: WebSocketSession): Mono<Void> {

        val sub = session.receive()
            .map { message -> handle(message) }
            .then()

        val pub = session.send(
            outbound.asFlux().map { msg -> session.binaryMessage { factory -> factory.wrap(msg) } }
        )

        return Mono.zip(sub, pub).then()
    }
}

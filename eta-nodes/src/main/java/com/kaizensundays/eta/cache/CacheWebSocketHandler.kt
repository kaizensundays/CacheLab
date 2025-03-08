package com.kaizensundays.eta.cache

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import reactor.core.publisher.Sinks

/**
 * Created: Saturday 1/27/2024, 5:04 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class CacheWebSocketHandler(private val handler: CacheCommandHandler) : AbstractWebSocketHandler() {

    val jsonConverter = JsonMapper.builder()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build()

    override fun handle(bytes: ByteArray, outbound: Sinks.Many<ByteArray>) {
        val json = String(bytes)
        logger.debug("msg={}", json)

        val msg = jsonConverter.readValue(json, Msg::class.java)

        val result = if (msg is CacheValue) {
            handler.execute(msg.value ?: "")
        } else {
            val response = handler.execute(msg)
            jsonConverter.writeValueAsString(response)
        }
        outbound.tryEmitNext(result.toByteArray())

    }

}
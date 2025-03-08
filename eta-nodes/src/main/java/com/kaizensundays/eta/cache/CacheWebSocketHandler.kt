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
        logger.debug("msg={}", String(bytes))

        val msg = jsonConverter.readValue(bytes, Msg::class.java)

        if (msg is CacheValue) {
            val result = handler.execute(msg.value ?: "")
            outbound.tryEmitNext(result.toByteArray())
        } else {
            handler.execute(msg)
        }

    }

}
package com.kaizensundays.eta.cache

import reactor.core.publisher.Sinks

/**
 * Created: Saturday 1/27/2024, 5:04 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class CacheWebSocketHandler(private val handler: CacheCommandHandler) : AbstractWebSocketHandler() {

    override fun handle(bytes: ByteArray, outbound: Sinks.Many<ByteArray>) {
        val json = String(bytes)
        logger.debug("msg={}", json)

        val msg = jsonConverter.readValue(json, Msg::class.java)

        val response = handler.execute(msg)
        val result = jsonConverter.writeValueAsString(response)

        outbound.tryEmitNext(result.toByteArray())
    }

}
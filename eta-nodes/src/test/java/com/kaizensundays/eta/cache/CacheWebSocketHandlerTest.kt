package com.kaizensundays.eta.cache

import com.kaizensundays.eta.cache.messages.CacheValue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import reactor.core.publisher.Sinks

/**
 * Created: Sunday 10/6/2024, 1:48 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class CacheWebSocketHandlerTest {

    private val handler: CacheCommandHandler = mock()
    private val sink: Sinks.Many<ByteArray> = mock()
    private val webSocketHandler = CacheWebSocketHandler(handler)

    @Test
    fun pingPong() {

        val req = CacheValue("ping", 0)
        val json = webSocketHandler.jsonConverter.writeValueAsString(req)
        val pong = "pong"

        whenever(handler.execute(req)).thenReturn(Response(0, "Ok", 0))
        whenever(sink.tryEmitNext(any())).thenReturn(Sinks.EmitResult.OK)

        webSocketHandler.handle(json.toByteArray(), sink)

        verify(handler).execute(any<Msg>())
        verify(sink).tryEmitNext(any<ByteArray>())
        verifyNoMoreInteractions(handler)
        verifyNoMoreInteractions(sink)
    }

}
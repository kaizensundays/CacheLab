package com.kaizensundays.eta.cache

import com.kaizensundays.eta.grpc.EndpointGrpc
import com.kaizensundays.eta.grpc.GetRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.junit.jupiter.api.Test

/**
 * Created: Sunday 5/18/2025, 1:29 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class GrpcServiceRemoteTest {

    @Test
    fun test() {

        val channel: ManagedChannel = ManagedChannelBuilder
            .forAddress("localhost", 7801)
            .usePlaintext()
            .build()

        val stub = EndpointGrpc.newBlockingStub(channel)

        try {
            val request = GetRequest.newBuilder().build()

            stub.getStream(request).forEach { cacheValue ->
                println("Received: ${cacheValue.value.toStringUtf8()}")
            }
            println("Stream completed")
        } catch (e: Exception) {
            println("Error consuming stream: ${e.message}")
        } finally {
            channel.shutdown()
        }

    }


}
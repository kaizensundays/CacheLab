package com.kaizensundays.eta.cache

import com.kaizensundays.eta.grpc.EndpointGrpc
import com.kaizensundays.eta.grpc.GetRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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

        val stub = EndpointGrpc.newStub(channel)

        try {
            val latch = CountDownLatch(1)

            val request = GetRequest.newBuilder().build()

            stub.getStream(request, object : StreamObserver<com.kaizensundays.eta.grpc.CacheValue> {
                override fun onNext(cacheValue: com.kaizensundays.eta.grpc.CacheValue) {
                    println("Received: ${cacheValue.value}")
                }

                override fun onError(t: Throwable) {
                    println("Error consuming stream: ${t.message}")
                }

                override fun onCompleted() {
                    println("Stream completed")
                    latch.countDown()
                }
            })

            latch.await(30, TimeUnit.SECONDS)
        } catch (e: Exception) {
            println("Error consuming stream: ${e.message}")
        } finally {
            channel.shutdown()
        }

    }


}
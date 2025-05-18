package com.kaizensundays.eta.cache

import com.kaizensundays.eta.grpc.CacheValue
import com.kaizensundays.eta.grpc.EndpointGrpc
import com.kaizensundays.eta.grpc.GetRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

/**
 * Created: Sunday 5/18/2025, 1:29 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class GrpcServiceRemoteTest {

    fun getStream(request: GetRequest, channel: ManagedChannel): Flux<CacheValue> {

        return Flux.create { sink ->

            val stub = EndpointGrpc.newStub(channel)

            stub.getStream(request, object : StreamObserver<CacheValue> {
                override fun onNext(cacheValue: CacheValue) {
                    sink.next(cacheValue)
                }

                override fun onError(t: Throwable) {
                    sink.error(t)
                }

                override fun onCompleted() {
                    sink.complete()
                }
            })

        }
    }

    @Test
    fun getStream() {

        val channel: ManagedChannel = ManagedChannelBuilder
            .forAddress("localhost", 7801)
            .usePlaintext()
            .build()

        try {
            val request = GetRequest.newBuilder().build()

            getStream(request, channel)
                .take(4)
                .doOnNext { cacheValue ->
                    println("Received: ${cacheValue.value.toStringUtf8()}")
                }
                .blockLast(Duration.ofSeconds(30))
        } catch (e: Exception) {
            println("Error: ${e.message}")
        } finally {
            channel.shutdown()
        }

    }

}
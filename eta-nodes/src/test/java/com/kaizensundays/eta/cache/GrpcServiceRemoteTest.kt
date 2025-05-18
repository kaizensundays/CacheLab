package com.kaizensundays.eta.cache

import com.kaizensundays.eta.grpc.CacheValue
import com.kaizensundays.eta.grpc.EndpointGrpc
import com.kaizensundays.eta.grpc.GetRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

/**
 * Created: Sunday 5/18/2025, 1:29 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class GrpcServiceRemoteTest {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private fun getStream(request: GetRequest, channel: ManagedChannel): Flux<CacheValue> {

        return Flux.create { sink ->

            EndpointGrpc.newStub(channel)
                .getStream(request, object : StreamObserver<CacheValue> {
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
                .publishOn(Schedulers.parallel())
                .doOnNext { cacheValue ->
                    logger.info("Received: ${cacheValue.value.toStringUtf8()}")
                }
                .blockLast(Duration.ofSeconds(30))
        } catch (e: Exception) {
            logger.error("Error: ${e.message}", e)
        } finally {
            channel.shutdown()
        }

    }

}
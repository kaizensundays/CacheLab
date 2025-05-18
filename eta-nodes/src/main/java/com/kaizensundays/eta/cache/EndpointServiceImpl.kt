package com.kaizensundays.eta.cache

import com.google.protobuf.ByteString
import com.kaizensundays.eta.grpc.EndpointGrpc
import com.kaizensundays.eta.grpc.GetRequest
import io.grpc.stub.StreamObserver


/**
 * Created: Sunday 5/18/2025, 12:47 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class EndpointServiceImpl : EndpointGrpc.EndpointImplBase() {

    override fun getStream(request: GetRequest, responseObserver: StreamObserver<com.kaizensundays.eta.grpc.CacheValue>) {
        try {
            for (i in 0..4) {
                val cacheValue = com.kaizensundays.eta.grpc.CacheValue.newBuilder()
                    .setValue(ByteString.copyFromUtf8("Streamed value $i"))
                    .build()
                responseObserver.onNext(cacheValue)
                Thread.sleep(100)
            }
            responseObserver.onCompleted()
        } catch (e: InterruptedException) {
            responseObserver.onError(e)
        }
    }

}
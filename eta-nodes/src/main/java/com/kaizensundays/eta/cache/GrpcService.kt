package com.kaizensundays.eta.cache

import io.grpc.Server
import io.grpc.ServerBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import java.util.concurrent.TimeUnit


/**
 * Created: Sunday 5/18/2025, 12:46 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class GrpcService : InitializingBean, DisposableBean {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    private var server: Server? = null

    override fun afterPropertiesSet() {

        Runtime.getRuntime().addShutdownHook(Thread {
            try {
                this@GrpcService.destroy()
            } catch (e: InterruptedException) {
                e.printStackTrace(System.err)
            }
            logger.info("Server shut down")
        })

        val port = 7801
        server = ServerBuilder.forPort(port)
            .addService(EndpointServiceImpl())
            .build()
            .start()

        logger.info("Started, listening on $port")
    }

    override fun destroy() {
        if (server != null) {
            server?.shutdown()?.awaitTermination(30, TimeUnit.SECONDS)
        }
    }

}
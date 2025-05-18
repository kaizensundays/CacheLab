package com.kaizensundays.eta.cache

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.kaizensundays.eta.cache.messages.CacheValue
import com.kaizensundays.eta.context.Logger
import com.kaizensundays.messaging.DefaultLoadBalancer
import com.kaizensundays.messaging.Instance
import com.kaizensundays.messaging.LoadBalancer
import com.kaizensundays.messaging.WebFluxProducer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.net.URI
import java.time.Duration
import kotlin.test.assertTrue

/**
 * Created: Sunday 10/6/2024, 1:17 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Suppress("MemberVisibilityCanBePrivate")
@ActiveProfiles("test")
@ContextConfiguration(locations = ["/CacheIntegrationTest.xml"])
class CacheIntegrationTest : IntegrationTestSupport() {

    private val jsonConverter = JsonMapper.builder()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build()

    private lateinit var loadBalancer: LoadBalancer

    private lateinit var producer: WebFluxProducer

    @LocalServerPort
    var embeddedPort = 0
    val remotePort = 7701
    val remote = false

    @BeforeEach
    fun before() {
        val port = if (remote) remotePort else embeddedPort
        loadBalancer = DefaultLoadBalancer(listOf(Instance("localhost", port)))
        producer = WebFluxProducer(loadBalancer)
        logger.info("java.net.preferIPv4Stack={}", System.getProperty("java.net.preferIPv4Stack"))
    }

    @Test
    fun send() {

        val msg = jsonConverter.writeValueAsString(CacheValue("test", 0)).toByteArray()

        val topic = URI("ws:/default/ws?maxAttempts=3")

        val m = producer.send(topic, msg)

        val done = StepVerifier.create(m)
            .verifyComplete()

        assertTrue(done < Duration.ofSeconds(10))
    }

    @Test
    fun ping() {

        val messages = arrayOf(
            Heartbeat(),
            LogLevel(Logger.DEBUG),
            Heartbeat(),
        )

        val msgs = Flux.fromArray(messages)
            .map { msg -> jsonConverter.writeValueAsString(msg).toByteArray() }


        val topic = URI("ws:/default/ws?maxAttempts=3")

        val resp = producer.request(topic, msgs)
            .take(messages.size.toLong())
            .map { String(it) }
            .blockLast(Duration.ofSeconds(10))

        assertNotNull(resp)
        val msg = jsonConverter.readValue(resp, Msg::class.java)
        assertTrue(msg is Response)
        assertEquals("Ok", msg.text)
    }

    @Test
    fun cache() {

        val driver = TestDriver(producer)

        val keyNum = 10
        val messagesPerKey = 2
        val rounds = 1
        val total = keyNum * messagesPerKey * rounds

        val f = driver.execute(keyNum, messagesPerKey, rounds)

        val done = StepVerifier.create(f)
            .expectNextCount(total.toLong())
            .verifyComplete()

        assertTrue(done < Duration.ofSeconds(1000))
    }

}
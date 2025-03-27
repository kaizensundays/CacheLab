package com.kaizensundays.eta.nodes

import com.kaizensundays.eta.context.notImplemented

/**
 * Created: Sunday 2/9/2025, 11:20 AM Eastern Time
 *
 * @author Sergey Chuykov
 */

interface Producer

class OkHttpProducer : Producer

interface Initiator {
    fun connect()
    fun disconnect()
}

// Session
class SessionInitiator(producer: Producer) : Initiator {
    override fun connect() {
        notImplemented()
    }

    override fun disconnect() {
        notImplemented()
    }
}

interface Acceptor

class WebFluxAcceptor : Acceptor

package com.kaizensundays.eta.cache

/**
 * Created: Sunday 2/16/2025, 11:49 AM Eastern Time
 *
 * @author Sergey Chuykov
 */
@Suppress("ConstPropertyName")
object MsgType {

    const val Heartbeat = "Heartbeat"

    const val CacheGet = "CacheGet"
    const val CachePut = "CachePut"

    const val Response = "Response"
    const val CacheValue = "CacheValue"

}
package com.kaizensundays.eta.cache

import org.slf4j.spi.LocationAwareLogger

/**
 * Created: Sunday 3/16/2025, 12:59 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class LogLevel(val level: Int, seqNum: Int = 0) : Msg(MsgType.LogLevel, seqNum) {

    constructor() : this(LocationAwareLogger.DEBUG_INT, 0)

}
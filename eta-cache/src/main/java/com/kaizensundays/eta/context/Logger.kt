package com.kaizensundays.eta.context

import org.slf4j.LoggerFactory
import org.slf4j.spi.LocationAwareLogger

/**
 * Created: Sunday 3/16/2025, 12:46 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class Logger(type: Class<Any>) {

    private val logger: org.slf4j.Logger = LoggerFactory.getLogger(type)

    companion object {
        const val TRACE = LocationAwareLogger.TRACE_INT
        const val DEBUG = LocationAwareLogger.DEBUG_INT
        const val INFO = LocationAwareLogger.INFO_INT
        const val WARN = LocationAwareLogger.WARN_INT
        const val ERROR = LocationAwareLogger.ERROR_INT
    }

    @Volatile
    var level = DEBUG

    fun debug(format: String, vararg args: Any) {
        if (level <= DEBUG) {
            logger.debug(format, args)
        }
    }

    fun info(format: String, vararg args: Any) {
        if (level <= INFO) {
            logger.info(format, args)
        }
    }

    fun error(msg: String, t: Throwable) {
        if (level <= ERROR) {
            logger.error(msg, t)
        }
    }

}
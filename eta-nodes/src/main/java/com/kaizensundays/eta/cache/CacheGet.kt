package com.kaizensundays.eta.cache

/**
 * Created: Sunday 2/16/2025, 12:08 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class CacheGet(val key: String, seqNum: Int) : Msg(MsgType.CacheGet, seqNum) {

    constructor() : this("", 0)

}
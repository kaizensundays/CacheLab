package com.kaizensundays.eta.cache.messages

import com.kaizensundays.eta.cache.MsgType
import com.kaizensundays.eta.cache.Response

/**
 * Created: Sunday 2/16/2025, 12:57 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class CacheValue(val value: String?, seqNum: Int) : Response(0, "Ok", seqNum, MsgType.CacheValue) {

    constructor() : this(null, 0)

}
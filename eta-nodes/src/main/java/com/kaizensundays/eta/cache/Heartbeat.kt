package com.kaizensundays.eta.cache

/**
 * Created: Sunday 3/9/2025, 12:33 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class Heartbeat(seqNum: Int = 0) : Msg(MsgType.Heartbeat, seqNum)
package com.kaizensundays.eta.cache

import com.kaizensundays.eta.context.Context
import com.kaizensundays.eta.context.notImplemented
import java.net.URI
import java.util.*
import javax.cache.Cache
import javax.cache.CacheManager
import javax.cache.configuration.Configuration
import javax.cache.spi.CachingProvider

/**
 * Created: Sunday 1/5/2025, 5:25 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class CacheManagerImpl(
    private val uri: URI,
    private val cachingProvider: CachingProvider,
    private val clsLdr: ClassLoader,
    private val props: Properties
) : CacheManager {

    init {
        start()
    }

    lateinit var node: EtaCacheNode

    private fun start() {
        if (uri == cachingProvider.defaultURI) {

            val conf = EtaNodeConfiguration()
            conf.nodeName = "T"
            conf.members = mutableListOf(conf.nodeName)

            node = Context.cacheNode(conf)
            node.init()

        } else {
            throw UnsupportedOperationException()
        }
    }

    override fun close() {
        node.destroy()
    }

    override fun getCachingProvider(): CachingProvider {
        notImplemented()
    }

    override fun getURI(): URI {
        notImplemented()
    }

    override fun getClassLoader(): ClassLoader {
        notImplemented()
    }

    override fun getProperties(): Properties {
        notImplemented()
    }

    // EtaCacheNodeImpl -> construct -> [ init(): caches.init ->  raftNode.connect - caches.connect() ]

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any?, V : Any?, C : Configuration<K, V>> createCache(cacheName: String?, configuration: C): Cache<K, V>? {

        val cacheConf: EtaCacheConfiguration<K, V> = configuration as EtaCacheConfiguration<K, V>

        val cache = node.getOrCreateCache(node.getNodeConfiguration(), cacheConf)

        cache.init()

        cache.connect()

        return cache
    }

    override fun <K : Any?, V : Any?> getCache(cacheName: String?, keyType: Class<K>?, valueType: Class<V>?): Cache<K, V> {
        notImplemented()
    }

    override fun <K : Any?, V : Any?> getCache(cacheName: String?): Cache<K, V> {
        notImplemented()
    }

    override fun getCacheNames(): MutableIterable<String> {
        notImplemented()
    }

    override fun destroyCache(cacheName: String?) {
        notImplemented()
    }

    override fun enableManagement(cacheName: String?, enabled: Boolean) {
        notImplemented()
    }

    override fun enableStatistics(cacheName: String?, enabled: Boolean) {
        notImplemented()
    }

    override fun isClosed(): Boolean {
        notImplemented()
    }

    override fun <T : Any?> unwrap(clazz: Class<T>?): T {
        notImplemented()
    }
}
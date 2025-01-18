package com.kaizensundays.eta.cache

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

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun getCachingProvider(): CachingProvider {
        TODO("Not yet implemented")
    }

    override fun getURI(): URI {
        TODO("Not yet implemented")
    }

    override fun getClassLoader(): ClassLoader {
        TODO("Not yet implemented")
    }

    override fun getProperties(): Properties {
        TODO("Not yet implemented")
    }

    override fun <K : Any?, V : Any?, C : Configuration<K, V>?> createCache(cacheName: String?, configuration: C): Cache<K, V>? {
        return null
    }

    override fun <K : Any?, V : Any?> getCache(cacheName: String?, keyType: Class<K>?, valueType: Class<V>?): Cache<K, V> {
        TODO("Not yet implemented")
    }

    override fun <K : Any?, V : Any?> getCache(cacheName: String?): Cache<K, V> {
        TODO("Not yet implemented")
    }

    override fun getCacheNames(): MutableIterable<String> {
        TODO("Not yet implemented")
    }

    override fun destroyCache(cacheName: String?) {
        TODO("Not yet implemented")
    }

    override fun enableManagement(cacheName: String?, enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun enableStatistics(cacheName: String?, enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isClosed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> unwrap(clazz: Class<T>?): T {
        TODO("Not yet implemented")
    }
}
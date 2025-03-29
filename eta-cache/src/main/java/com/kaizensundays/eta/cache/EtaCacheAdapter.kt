package com.kaizensundays.eta.cache

import com.kaizensundays.eta.context.notImplemented
import javax.cache.CacheManager
import javax.cache.configuration.Configuration
import javax.cache.integration.CompletionListener
import javax.cache.processor.EntryProcessor
import javax.cache.processor.EntryProcessorResult

/**
 * Created: Saturday 10/5/2024, 12:57 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
abstract class EtaCacheAdapter<K, V> : EtaCache<K, V> {

    override fun close() {
        notImplemented()
    }

    override fun removeAll() {
        notImplemented()
    }

    override fun clear() {
        notImplemented()
    }

    override fun getName(): String {
        return "?"
    }

    override fun getCacheManager(): CacheManager {
        notImplemented()
    }

    override fun isClosed(): Boolean {
        notImplemented()
    }

    override fun <T : Any?> unwrap(clazz: Class<T>?): T {
        notImplemented()
    }

    override fun <T : Any?> invokeAll(keys: MutableSet<out K>?, entryProcessor: EntryProcessor<K, V, T>?, vararg arguments: Any?): MutableMap<K, EntryProcessorResult<T>> {
        notImplemented()
    }

    override fun <T : Any?> invoke(key: K, entryProcessor: EntryProcessor<K, V, T>?, vararg arguments: Any?): T {
        notImplemented()
    }

    override fun <C : Configuration<K, V>?> getConfiguration(clazz: Class<C>?): C {
        notImplemented()
    }

    override fun removeAll(keys: MutableSet<out K>?) {
        notImplemented()
    }

    override fun getAndReplace(key: K, value: V): V {
        notImplemented()
    }

    override fun replace(key: K, value: V): Boolean {
        notImplemented()
    }

    override fun replace(key: K, oldValue: V, newValue: V): Boolean {
        notImplemented()
    }

    override fun getAndRemove(key: K): V {
        notImplemented()
    }

    override fun remove(key: K, oldValue: V): Boolean {
        notImplemented()
    }

    override fun remove(key: K): Boolean {
        notImplemented()
    }

    override fun putIfAbsent(key: K, value: V): Boolean {
        notImplemented()
    }

    override fun putAll(map: MutableMap<out K, out V>?) {
        notImplemented()
    }

    override fun getAndPut(key: K, value: V): V {
        notImplemented()
    }

    override fun put(key: K, value: V) {
        notImplemented()
    }

    override fun loadAll(keys: MutableSet<out K>?, replaceExistingValues: Boolean, completionListener: CompletionListener?) {
        notImplemented()
    }

    override fun containsKey(key: K): Boolean {
        notImplemented()
    }

    override fun getAll(keys: MutableSet<out K>?): MutableMap<K, V> {
        notImplemented()
    }

    override fun get(key: K): V {
        notImplemented()
    }
}
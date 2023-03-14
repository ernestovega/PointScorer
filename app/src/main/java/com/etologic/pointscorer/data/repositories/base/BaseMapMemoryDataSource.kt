package com.etologic.pointscorer.data.repositories.base

abstract class BaseMapMemoryDataSource<K, V> : BaseMemoryDataSource<MutableMap<K, V>>() {

    open suspend fun get(key: K): V? = get()?.let { it[key] }

    open suspend fun save(key: K, newValue: V): V {
        if (get() == null) {
            save(mutableMapOf())
        }
        get()!![key] = newValue
        return get()!![key]!!
    }

    open suspend fun clear(key: K) {
        get()?.remove(key)
    }
}

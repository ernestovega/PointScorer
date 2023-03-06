package com.etologic.pointscorer.data.repositories.base

abstract class BaseMapMemoryDataSource<K, V>: BaseMemoryDataSource<MutableMap<K, V>>() {

    open fun get(key: K): V? = get()?.let { it[key] }

    open fun save(key: K, newValue: V): V {
        if (get() == null) { save(mutableMapOf()) }
        get()!![key] = newValue
        return get()!![key]!!
    }

}

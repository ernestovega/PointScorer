package com.etologic.pointscorer.data.repositories.base

abstract class BaseMemoryDataSource<T> {

    private var value: T? = null

    open suspend fun get(): T? = value

    open suspend fun save(newValue: T) {
        value = newValue
    }

    open suspend fun clear() {
        value = null
    }

}

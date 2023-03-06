package com.etologic.pointscorer.data.repositories.base

abstract class BaseMemoryDataSource<T> {

    private var value: T? = null

    open fun get(): T? = value

    open fun save(newValue: T) {
        value = newValue
    }

}

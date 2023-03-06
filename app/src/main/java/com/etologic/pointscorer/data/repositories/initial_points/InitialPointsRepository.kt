package com.etologic.pointscorer.data.repositories.initial_points

import com.etologic.pointscorer.common.Constants.DEFAULT_INITIAL_POINTS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class InitialPointsRepository
@Inject internal constructor(
    private val memoryDataSource: InitialPointsMemoryDataSource,
    private val dataStoreDataSource: InitialPointsDataStoreDataSource
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    suspend fun getInitialPoints(): Int =
        memoryDataSource.get()
            ?: (dataStoreDataSource.get() ?: DEFAULT_INITIAL_POINTS)
                .also { initialPoints -> memoryDataSource.save(initialPoints) }

    suspend fun saveInitialPoints(newInitialPoints: Int) {
        memoryDataSource.save(newInitialPoints)
        dataStoreDataSource.save(newInitialPoints)
    }

    fun invalidate() {
        coroutineContext.job.cancel()
    }

}
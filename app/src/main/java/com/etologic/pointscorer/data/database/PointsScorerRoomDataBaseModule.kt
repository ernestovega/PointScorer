package com.etologic.pointscorer.data.database

import android.content.Context
import androidx.room.Room
import com.etologic.pointscorer.data.repositories.players.PlayersDataBaseDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PointsScorerRoomDataBaseModule {

    companion object {
        private const val DATABASE_NAME = "points_scorer_room_database"
    }

    @Provides
    @Singleton
    fun providePointsScorerRoomDataBase(
        @ApplicationContext appContext: Context
    ): PointsScorerRoomDataBase =
        Room.databaseBuilder(
            context = appContext,
            klass = PointsScorerRoomDataBase::class.java,
            name = DATABASE_NAME
        ).build()

    @Provides
    fun providePlayersDao(dataBase: PointsScorerRoomDataBase): PlayersDataBaseDataSource =
        dataBase.playersDao()

}
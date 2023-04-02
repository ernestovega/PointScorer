package com.etologic.pointscorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.etologic.pointscorer.bussiness.model.Player
import com.etologic.pointscorer.data.repositories.players.PlayersDataBaseDataSource

@Database(
    entities = [Player::class],
    version = 1
)
@TypeConverters(
    RoomTypeConverters::class
)
abstract class PointsScorerRoomDataBase : RoomDatabase() {

    abstract fun playersDao(): PlayersDataBaseDataSource

}
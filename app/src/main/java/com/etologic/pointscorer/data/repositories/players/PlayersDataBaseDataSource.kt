package com.etologic.pointscorer.data.repositories.players

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.etologic.pointscorer.bussiness.model.entities.Player

@Dao
interface PlayersDataBaseDataSource {

    @Insert
    suspend fun insert(player: Player)

    @Query("SELECT * from players where id = :playerId")
    suspend fun get(playerId: Int): Player?

    @Query("UPDATE players SET name = :newName WHERE id = :playerId")
    suspend fun updateName(playerId: Int, newName: String)

    @Query("UPDATE players SET color = :newColor WHERE id = :playerId")
    suspend fun updateColor(playerId: Int, newColor: Int)

    @Query("UPDATE players SET background = :newBackground WHERE id = :playerId")
    suspend fun updateBackground(playerId: Int, newBackground: String)

    @Query("UPDATE players SET points = :newPoints WHERE id = :playerId")
    suspend fun updatePoints(playerId: Int, newPoints: Int)

    @Query("DELETE FROM players")
    suspend fun deleteAll()

}
package com.etologic.pointscorer.data.repositories.players

import javax.inject.Inject

class PlayersMemoryDataSource
@Inject constructor() {
    
    private var initialPoints: Int? = null
    private var playersPoints: MutableMap<Int, Int> = mutableMapOf()
    private var playersNames: MutableMap<Int, String> = mutableMapOf()
    private var playersColors: MutableMap<Int, Int> = mutableMapOf()
    
    suspend fun getInitialPoints() = initialPoints
    
    suspend fun getPlayerPoints(playerId: Int) = playersPoints[playerId]
    
    suspend fun getPlayerName(playerId: Int) = playersNames[playerId]
    
    suspend fun getPlayerColor(playerId: Int) = playersColors[playerId]
    
    suspend fun saveInitialPoints(initialPoints: Int) {
        this.initialPoints = initialPoints
    }
    
    suspend fun savePlayerPoints(playerId: Int, points: Int) {
        playersPoints[playerId] = points
    }
    
    suspend fun savePlayerName(playerId: Int, name: String) {
        playersNames[playerId] = name
    }
    
    suspend fun savePlayerColor(playerId: Int, color: Int) {
        playersColors[playerId] = color
    }
    
}

package com.etologic.pointscorer.data.repositories.players

import javax.inject.Inject

class PlayersMemoryDataSource
@Inject constructor() {
    
    private var initialPoints: Int? = null
    private var playersPoints: MutableMap<Int, Int> = mutableMapOf()
    private var playersNames: MutableMap<Int, String> = mutableMapOf()
    private var playersColors: MutableMap<Int, Int> = mutableMapOf()
    
    fun getInitialPoints() = initialPoints
    
    fun getPlayerPoints(playerId: Int) = playersPoints[playerId]
    
    fun getPlayerName(playerId: Int) = playersNames[playerId]
    
    fun getPlayerColor(playerId: Int) = playersColors[playerId]
    
    fun saveInitialPoints(initialPoints: Int) {
        this.initialPoints = initialPoints
    }
    
    fun savePlayerPoints(playerId: Int, points: Int) {
        playersPoints[playerId] = points
    }
    
    fun savePlayerName(playerId: Int, name: String) {
        playersNames[playerId] = name
    }
    
    fun savePlayerColor(playerId: Int, color: Int) {
        playersColors[playerId] = color
    }
}

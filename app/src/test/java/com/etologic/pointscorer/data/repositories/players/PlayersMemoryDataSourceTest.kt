package com.etologic.pointscorer.data.repositories.players

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PlayersMemoryDataSourceTest {

    private val dataSource = PlayersMemoryDataSource()

    @Test
    fun `everything is null before being saved`() {
        assertThat(dataSource.getInitialPoints()).isNull()
        assertThat(dataSource.getPlayerPoints(11)).isNull()
        assertThat(dataSource.getPlayerName(11)).isNull()
        assertThat(dataSource.getPlayerColor(11)).isNull()
    }

    @Test
    fun `saves and returns the initial points`() {
        dataSource.saveInitialPoints(40)

        assertThat(dataSource.getInitialPoints()).isEqualTo(40)
    }

    @Test
    fun `keeps every player's values apart`() {
        dataSource.savePlayerPoints(21, 5)
        dataSource.savePlayerPoints(22, -3)
        dataSource.savePlayerName(21, "Ana")
        dataSource.savePlayerName(22, "Luis")
        dataSource.savePlayerColor(21, 0xFF0000)
        dataSource.savePlayerColor(22, 0x00FF00)

        assertThat(dataSource.getPlayerPoints(21)).isEqualTo(5)
        assertThat(dataSource.getPlayerPoints(22)).isEqualTo(-3)
        assertThat(dataSource.getPlayerName(21)).isEqualTo("Ana")
        assertThat(dataSource.getPlayerName(22)).isEqualTo("Luis")
        assertThat(dataSource.getPlayerColor(21)).isEqualTo(0xFF0000)
        assertThat(dataSource.getPlayerColor(22)).isEqualTo(0x00FF00)
        assertThat(dataSource.getPlayerPoints(23)).isNull()
    }

    @Test
    fun `overwrites a previous value`() {
        dataSource.savePlayerPoints(11, 1)
        dataSource.savePlayerPoints(11, 2)

        assertThat(dataSource.getPlayerPoints(11)).isEqualTo(2)
    }
}

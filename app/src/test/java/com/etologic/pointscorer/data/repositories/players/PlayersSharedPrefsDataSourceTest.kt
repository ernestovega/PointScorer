package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayersSharedPrefsDataSourceTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val dataSource = PlayersSharedPrefsDataSource(context)

    @Test
    fun `returns the given defaults when nothing was saved`() = runTest {
        assertThat(dataSource.getInitialPoints(100)).isEqualTo(100)
        assertThat(dataSource.getPlayerPoints(11, 42)).isEqualTo(42)
        assertThat(dataSource.getPlayerName(11, "Player name")).isEqualTo("Player name")
        assertThat(dataSource.getPlayerColor(11, 7)).isEqualTo(7)
    }

    @Test
    fun `persists values across instances`() = runTest {
        dataSource.saveInitialPoints(20)
        dataSource.savePlayerPoints(31, 15)
        dataSource.savePlayerName(31, "Marta")
        dataSource.savePlayerColor(31, 0x123456)

        val otherInstance = PlayersSharedPrefsDataSource(context)

        assertThat(otherInstance.getInitialPoints(100)).isEqualTo(20)
        assertThat(otherInstance.getPlayerPoints(31, 0)).isEqualTo(15)
        assertThat(otherInstance.getPlayerName(31, "")).isEqualTo("Marta")
        assertThat(otherInstance.getPlayerColor(31, 0)).isEqualTo(0x123456)
    }

    @Test
    fun `stores every player under its own key`() = runTest {
        dataSource.savePlayerPoints(41, 1)
        dataSource.savePlayerPoints(42, 2)
        dataSource.savePlayerName(41, "A")
        dataSource.savePlayerName(42, "B")

        assertThat(dataSource.getPlayerPoints(41, 0)).isEqualTo(1)
        assertThat(dataSource.getPlayerPoints(42, 0)).isEqualTo(2)
        assertThat(dataSource.getPlayerName(41, "")).isEqualTo("A")
        assertThat(dataSource.getPlayerName(42, "")).isEqualTo("B")
        assertThat(dataSource.getPlayerPoints(43, -1)).isEqualTo(-1)
    }
}

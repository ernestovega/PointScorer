package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.etologic.pointscorer.R
import com.etologic.pointscorer.data.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import com.etologic.pointscorer.data.repositories.players.PlayersRepository.Companion.DEFAULT_INITIAL_POINTS
import com.etologic.pointscorer.data.repositories.players.PlayersRepository.Companion.MAX_POINTS
import com.etologic.pointscorer.data.repositories.players.PlayersRepository.Companion.MIN_POINTS
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith

/** Repository + both real data sources, backed by Robolectric's SharedPreferences. */
@RunWith(AndroidJUnit4::class)
class PlayersRepositoryTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val repository = newRepository()

    /** A new repository has an empty memory cache, like after the app process is restarted. */
    private fun newRepository() =
        PlayersRepository(context, PlayersMemoryDataSource(), PlayersSharedPrefsDataSource(context))

    @Test
    fun `initial points default to 100`() = runTest {
        assertThat(repository.getInitialPoints()).isEqualTo(DEFAULT_INITIAL_POINTS)
        assertThat(DEFAULT_INITIAL_POINTS).isEqualTo(100)
    }

    @Test
    fun `saved initial points survive an app restart`() = runTest {
        repository.saveInitialPoints(30)

        assertThat(repository.getInitialPoints()).isEqualTo(30)
        assertThat(newRepository().getInitialPoints()).isEqualTo(30)
    }

    @Test
    fun `a player starts with the initial points`() = runTest {
        repository.saveInitialPoints(25)

        assertThat(repository.getPlayerPoints(11)).isEqualTo(25)
    }

    @Test
    fun `plus one and minus one change the points and persist them`() = runTest {
        assertThat(repository.plus1PlayerPoint(21)).isEqualTo(101)
        assertThat(repository.plus1PlayerPoint(21)).isEqualTo(102)
        assertThat(repository.minus1PlayerPoint(21)).isEqualTo(101)

        assertThat(newRepository().getPlayerPoints(21)).isEqualTo(101)
    }

    @Test
    fun `players do not share points`() = runTest {
        repository.plus1PlayerPoint(21)

        assertThat(repository.getPlayerPoints(21)).isEqualTo(101)
        assertThat(repository.getPlayerPoints(22)).isEqualTo(100)
    }

    @Test
    fun `can reach the maximum but not go over it`() = runTest {
        repository.saveInitialPoints(MAX_POINTS - 1)

        assertThat(repository.plus1PlayerPoint(11)).isEqualTo(MAX_POINTS)
        assertThrows(MaxPointsReachedException::class.java) {
            kotlinx.coroutines.runBlocking { repository.plus1PlayerPoint(11) }
        }
        assertThat(repository.getPlayerPoints(11)).isEqualTo(MAX_POINTS)
    }

    @Test
    fun `can reach the minimum but not go under it`() = runTest {
        repository.saveInitialPoints(MIN_POINTS + 1)

        assertThat(repository.minus1PlayerPoint(11)).isEqualTo(MIN_POINTS)
        assertThrows(MinPointsReachedException::class.java) {
            kotlinx.coroutines.runBlocking { repository.minus1PlayerPoint(11) }
        }
        assertThat(repository.getPlayerPoints(11)).isEqualTo(MIN_POINTS)
    }

    @Test
    fun `limits are -999 and 9999`() {
        assertThat(MIN_POINTS).isEqualTo(-999)
        assertThat(MAX_POINTS).isEqualTo(9999)
    }

    @Test
    fun `reset player points goes back to the current initial points`() = runTest {
        repository.plus1PlayerPoint(31)
        repository.plus1PlayerPoint(31)
        repository.saveInitialPoints(50)

        repository.resetPlayerPoints(31)

        assertThat(repository.getPlayerPoints(31)).isEqualTo(50)
        assertThat(newRepository().getPlayerPoints(31)).isEqualTo(50)
    }

    @Test
    fun `restore all players points resets every player of every game`() = runTest {
        val allPlayerIds = (1..8).flatMap { players -> (1..players).map { "$players$it".toInt() } }
        allPlayerIds.forEach { repository.plus1PlayerPoint(it) }
        repository.saveInitialPoints(10)

        repository.restoreAllPlayersPoints()

        val restarted = newRepository()
        allPlayerIds.forEach {
            assertThat(repository.getPlayerPoints(it)).isEqualTo(10)
            assertThat(restarted.getPlayerPoints(it)).isEqualTo(10)
        }
    }

    @Test
    fun `restore all players points keeps names and colors`() = runTest {
        repository.savePlayerName(21, "Ana")
        repository.savePlayerColor(21, 0xFF00FF)

        repository.restoreAllPlayersPoints()

        assertThat(repository.getPlayerName(21)).isEqualTo("Ana")
        assertThat(repository.getPlayerColor(21)).isEqualTo(0xFF00FF)
    }

    @Test
    fun `name defaults to the player name string and persists once saved`() = runTest {
        assertThat(repository.getPlayerName(11)).isEqualTo(context.getString(R.string.player_name))

        repository.savePlayerName(11, "Ernesto")

        assertThat(repository.getPlayerName(11)).isEqualTo("Ernesto")
        assertThat(newRepository().getPlayerName(11)).isEqualTo("Ernesto")
    }

    @Test
    fun `color defaults to white and persists once saved`() = runTest {
        val white = ContextCompat.getColor(context, R.color.white)
        val red = ContextCompat.getColor(context, R.color.red)
        assertThat(repository.getPlayerColor(11)).isEqualTo(white)

        repository.savePlayerColor(11, red)

        assertThat(repository.getPlayerColor(11)).isEqualTo(red)
        assertThat(newRepository().getPlayerColor(11)).isEqualTo(red)
    }
}

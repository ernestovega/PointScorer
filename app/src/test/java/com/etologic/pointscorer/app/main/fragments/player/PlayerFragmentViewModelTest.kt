package com.etologic.pointscorer.app.main.fragments.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.etologic.pointscorer.data.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import com.etologic.pointscorer.testutils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class PlayerFragmentViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: PlayersRepository = mockk(relaxed = true)
    private val viewModel = PlayerFragmentViewModel(repository)

    private fun givenPlayer(id: Int = PLAYER_ID, points: Int = 100, name: String = "Ana", color: Int = 0xFFFFFF) {
        coEvery { repository.getPlayerPoints(id) } returns points
        coEvery { repository.getPlayerName(id) } returns name
        coEvery { repository.getPlayerColor(id) } returns color
        viewModel.initScreen(id)
    }

    @Test
    fun `init screen publishes the stored name, color and points`() {
        givenPlayer(points = 42, name = "Luis", color = 0x00FF00)

        assertThat(viewModel.playerId).isEqualTo(PLAYER_ID)
        assertThat(viewModel.livePlayerPoints().value).isEqualTo(42)
        assertThat(viewModel.livePlayerName().value).isEqualTo("Luis")
        assertThat(viewModel.livePlayerColor().value).isEqualTo(0x00FF00)
    }

    @Test
    fun `init screen without a player id does nothing`() {
        viewModel.initScreen(null)

        assertThat(viewModel.livePlayerPoints().value).isNull()
        coVerify(exactly = 0) { repository.getPlayerPoints(any()) }
    }

    @Test
    fun `the number of players of the game comes from the first digit of the id`() {
        mapOf(11 to 1, 21 to 2, 35 to 3, 47 to 4, 52 to 5, 66 to 6, 71 to 7, 88 to 8).forEach { (id, players) ->
            val vm = PlayerFragmentViewModel(repository)
            vm.playerId = id
            assertThat(vm.gamePlayersNum).isEqualTo(players)
        }
    }

    @Test
    fun `up adds a point and counts it`() {
        givenPlayer()
        coEvery { repository.plus1PlayerPoint(PLAYER_ID) } returnsMany listOf(101, 102)

        viewModel.upClicked()
        assertThat(viewModel.livePlayerPoints().value).isEqualTo(101)
        assertThat(viewModel.livePlayerCount().value).isEqualTo(1)
        assertThat(viewModel.playerCountEnabled).isTrue()

        viewModel.upClicked()
        assertThat(viewModel.livePlayerPoints().value).isEqualTo(102)
        assertThat(viewModel.livePlayerCount().value).isEqualTo(2)
    }

    @Test
    fun `down removes a point and counts it`() {
        givenPlayer()
        coEvery { repository.minus1PlayerPoint(PLAYER_ID) } returnsMany listOf(99, 98)

        viewModel.downClicked()
        viewModel.downClicked()

        assertThat(viewModel.livePlayerPoints().value).isEqualTo(98)
        assertThat(viewModel.livePlayerCount().value).isEqualTo(-2)
        assertThat(viewModel.playerCountEnabled).isTrue()
    }

    @Test
    fun `up and down cancel each other in the count`() {
        givenPlayer()
        coEvery { repository.plus1PlayerPoint(PLAYER_ID) } returns 101
        coEvery { repository.minus1PlayerPoint(PLAYER_ID) } returns 100

        viewModel.upClicked()
        viewModel.downClicked()

        assertThat(viewModel.livePlayerPoints().value).isEqualTo(100)
        assertThat(viewModel.livePlayerCount().value).isEqualTo(0)
    }

    @Test
    fun `up at the maximum keeps the points and disables the count`() {
        givenPlayer(points = 9999)
        coEvery { repository.plus1PlayerPoint(PLAYER_ID) } throws MaxPointsReachedException()

        viewModel.upClicked()

        assertThat(viewModel.livePlayerPoints().value).isEqualTo(9999)
        assertThat(viewModel.livePlayerCount().value).isEqualTo(0)
        assertThat(viewModel.playerCountEnabled).isFalse()
    }

    @Test
    fun `down at the minimum keeps the points and disables the count`() {
        givenPlayer(points = -999)
        coEvery { repository.minus1PlayerPoint(PLAYER_ID) } throws MinPointsReachedException()

        viewModel.downClicked()

        assertThat(viewModel.livePlayerPoints().value).isEqualTo(-999)
        assertThat(viewModel.livePlayerCount().value).isEqualTo(0)
        assertThat(viewModel.playerCountEnabled).isFalse()
    }

    @Test
    fun `count animation end resets the count`() {
        givenPlayer()
        coEvery { repository.plus1PlayerPoint(PLAYER_ID) } returns 101
        viewModel.upClicked()

        viewModel.countAnimationEnded()

        assertThat(viewModel.livePlayerCount().value).isEqualTo(0)
        assertThat(viewModel.playerCountEnabled).isFalse()
    }

    @Test
    fun `saving the name stores and publishes it`() {
        givenPlayer()

        viewModel.savePlayerName("Marta")

        coVerify { repository.savePlayerName(PLAYER_ID, "Marta") }
        assertThat(viewModel.livePlayerName().value).isEqualTo("Marta")
    }

    @Test
    fun `saving the color stores and publishes it`() {
        givenPlayer()

        viewModel.savePlayerColor(0xFF0000)

        coVerify { repository.savePlayerColor(PLAYER_ID, 0xFF0000) }
        assertThat(viewModel.livePlayerColor().value).isEqualTo(0xFF0000)
    }

    @Test
    fun `restore points resets the player to the initial points`() {
        givenPlayer(points = 130)
        coEvery { repository.getInitialPoints() } returns 100

        viewModel.restorePlayerPoints()

        coVerifyOrder {
            repository.getInitialPoints()
            repository.resetPlayerPoints(PLAYER_ID)
        }
        assertThat(viewModel.livePlayerPoints().value).isEqualTo(100)
    }

    private companion object {
        const val PLAYER_ID = 42
    }
}

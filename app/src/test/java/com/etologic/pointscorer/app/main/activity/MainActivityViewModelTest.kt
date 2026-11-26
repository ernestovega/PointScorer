package com.etologic.pointscorer.app.main.activity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.FINISH
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.MENU
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import com.etologic.pointscorer.testutils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: PlayersRepository = mockk(relaxed = true)
    private val viewModel = MainActivityViewModel(repository)

    @Test
    fun `starts on the menu`() {
        assertThat(viewModel.liveScreen.value).isEqualTo(MENU)
    }

    @Test
    fun `navigates to every game screen`() {
        MainScreens.entries.forEach {
            viewModel.navigateTo(it)
            assertThat(viewModel.liveScreen.value).isEqualTo(it)
        }
    }

    @Test
    fun `back from a game goes to the menu`() {
        viewModel.navigateTo(MainScreens.FOUR_PLAYER)

        viewModel.navigateBack()

        assertThat(viewModel.liveScreen.value).isEqualTo(MENU)
    }

    @Test
    fun `back from the menu finishes the app`() {
        viewModel.navigateBack()

        assertThat(viewModel.liveScreen.value).isEqualTo(FINISH)
    }

    @Test
    fun `loads the initial points from the repository`() {
        coEvery { repository.getInitialPoints() } returns 77

        viewModel.getInitialPoints()

        assertThat(viewModel.liveInitialPoints.value).isEqualTo(77)
    }

    @Test
    fun `saves the initial points and publishes them`() {
        viewModel.saveInitialPoints(250)

        coVerify { repository.saveInitialPoints(250) }
        assertThat(viewModel.liveInitialPoints.value).isEqualTo(250)
    }

    @Test
    fun `restore all games points delegates to the repository`() {
        viewModel.restoreAllGamesPoints()

        coVerify(exactly = 1) { repository.restoreAllPlayersPoints() }
    }

    @Test
    fun `restore one game points signals the game and then clears the signal`() {
        val received = mutableListOf<Int>()
        viewModel.liveShouldRestoreAllPoints.observeForever { received += it }

        viewModel.restoreOneGamePoints(3)

        assertThat(received).containsExactly(3, 0).inOrder()
        assertThat(viewModel.liveShouldRestoreAllPoints.value).isEqualTo(0)
    }
}

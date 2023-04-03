package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.common.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class Add1PointToAPlayerUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var add1PointToAPlayerUseCase: Add1PointToAPlayerUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `Add 1 point to a player`() {
        runTest {
            val expectedValue = Constants.DEFAULT_INITIAL_POINTS + 1
            launch {
                val resultedValue = add1PointToAPlayerUseCase.invoke(0)
                assertEquals(expectedValue,)
            }
        }
    }

}
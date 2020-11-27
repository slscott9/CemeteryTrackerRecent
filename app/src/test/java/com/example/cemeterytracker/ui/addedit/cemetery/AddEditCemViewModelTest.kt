package com.example.cemeterytracker.ui.addedit.cemetery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cemeterytracker.CoroutineTestRule
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.getOrAwaitValue2
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class AddEditCemViewModelTest {


    private lateinit var viewModel: AddEditCemViewModel
    private lateinit var repository: Repository
    private lateinit var cemetery: Cemetery

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        repository = mock()

        viewModel = AddEditCemViewModel(repository)

        cemetery = Cemetery(
                cemeteryId = 2L,
                name = "unsynced cemetery",
                location = "15472",
                state = "AL",
                township = "thorsby",
                range = "r",
                spot = "spot",
                firstYear = "1600",
                section = "section",
                isSynced = false,
                graveCount = 0,
                addedBy = "newUser@gmail.com",
                county = "Chilton",
                newCemetery = true
        )

    }

    @Test
    fun insertCemetery_posts_cemId() = runBlockingTest {

        whenever(repository.insertCemetery(any())).thenReturn(1L)

        viewModel.insertCemetery(cemetery)

        val value = viewModel.cemId.getOrAwaitValue2()

        assertEquals(1L, value)
    }


}
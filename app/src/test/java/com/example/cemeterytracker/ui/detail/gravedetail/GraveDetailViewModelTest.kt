package com.example.cemeterytracker.ui.detail.gravedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.cemeterytracker.CoroutineTestRule
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.getOrAwaitValue2
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.apache.maven.artifact.ant.InstallTask
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GraveDetailViewModelTest {

    private lateinit var viewModel: GraveDetailViewModel

    private lateinit var repository: Repository

    private lateinit var observer : Observer<Grave>





    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun setup() {
        repository = mock()

        observer = mock()

        viewModel = GraveDetailViewModel(repository)


    }


    @Test
    fun setGraveId_triggers_graveSelected()  {


        val graveLiveData = MutableLiveData(Grave(
                graveId = 1L,
                cemeteryId = 2L,
                firstName = "unsynced grave",
                lastName = "grave",
                birthDate = "",
                deathDate = "",
                marriageYear = "",
                comment = "",
                graveNumber = "",
                isSynced = false,
                addedBy = ""
        ))

        whenever(repository.getGraveWithId(any())).thenReturn(graveLiveData)

        viewModel.setGraveId(1L)


        assertEquals(graveLiveData.value, viewModel.graveSelected.getOrAwaitValue2())




    }




}
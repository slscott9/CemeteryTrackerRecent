package com.example.cemeterytracker.ui.addedit.grave

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.cemeterytracker.CoroutineTestRule
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.getOrAwaitValue2
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddEditGraveViewModelTest {

    private lateinit var viewModel: AddEditGraveViewModel

    //collaborators
    private lateinit var repository: Repository

    private lateinit var observer: Observer<Grave>
    private lateinit var observer2: Observer<Long>



    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun setup() {
        repository = mock()

        observer = mock()
        observer2 = mock()


        viewModel = AddEditGraveViewModel(repository)


    }

    @Test
    fun setGraveId_sets_gravid_live_data() {
        viewModel.setGraveId(1L)

        val value = viewModel.graveId.getOrAwaitValue2()

        assertEquals(1L, value)
    }

    @Test
    fun graveToEdit_switches_on_change_to_graveId()  {

        //transformations do not emit live data unless they have an observer
        //mock observer in order to make transformation emit

        val livedata = MutableLiveData(Grave(
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


        whenever(repository.getGraveWithId(any())).thenReturn(livedata)

        viewModel.setGraveId(1L)


        val value = viewModel.graveToEdit.getOrAwaitValue2()
        assertEquals(livedata.getOrAwaitValue2(), value)



    }

    
}
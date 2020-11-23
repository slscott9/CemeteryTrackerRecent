package com.example.cemeterytracker.data.local

import androidx.test.filters.SmallTest
import com.example.cemeterytracker.data.database.CemeteryDao
import com.example.cemeterytracker.data.database.CemeteryDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.cemeterytracker.data.CoroutineTestRule
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class CemDaoTest {

    private lateinit var database: CemeteryDatabase
    private lateinit var dao: CemeteryDao
    private lateinit var unSyncedCem: Cemetery
    private lateinit var syncedCem: Cemetery
    private lateinit var unSyncedGrave : Grave
    private lateinit var syncedGrave : Grave

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

//    @get:Rule
//    var couroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CemeteryDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = database.dao()

        unSyncedCem = Cemetery(
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


        //This grave belongs to unSyncedCem through foreign key cemetery id
        unSyncedGrave = Grave(
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
        )

        syncedCem = Cemetery(
                cemeteryId = 1L,
                name = "synced cemetery",
                location = "15472",
                state = "AL",
                township = "thorsby",
                range = "r",
                spot = "spot",
                firstYear = "1600",
                section = "section",
                isSynced = true,
                graveCount = 0,
                addedBy = "newUser@gmail.com",
                county = "Chilton",
                newCemetery = true
        )


        //Belongs to syncedCem through foreign key cemetery id
        syncedGrave = Grave(
                graveId = 2L,
                cemeteryId = 1L,
                firstName = "unsynced grave",
                lastName = "grave",
                birthDate = "",
                deathDate = "",
                marriageYear = "",
                comment = "",
                graveNumber = "",
                isSynced = false,
                addedBy = ""
        )




    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun dao_insertCemetery_inserts_cemetery() = runBlockingTest {

        dao.insertCemetery(syncedCem)
        val cemeteryToTest = dao.getCemWithId(1).getOrAwaitValue()

        assertThat(syncedCem).isEqualTo(cemeteryToTest)
    }


    @Test
    fun getUnsyncedCems_returns_unSynced_cems() = runBlockingTest {

        dao.insertCemetery(unSyncedCem)
        dao.insertCemetery(syncedCem)
        dao.insertGrave(unSyncedGrave)

        val unsyncedCemList = dao.getUnsyncedCems(false, true)

        assertThat(unsyncedCemList.size).isEqualTo(1)
        assertThat(unsyncedCemList[0].cemetery).isEqualTo(unSyncedCem)
        assertThat(unsyncedCemList[0].graves.size).isEqualTo(1)


    }


    //dao's getNewCemeteries should return list of CemeteryGrave objects with newCemetery property set to false
    @Test
    fun getNewCemeteries() = runBlockingTest {

        dao.insertCemetery(unSyncedCem)
        dao.insertCemetery(syncedCem)
        dao.insertGrave(unSyncedGrave)

        val cemeteryGraveList = dao.getNewCemeteries(true)

        assertThat(cemeteryGraveList.size).isEqualTo(2)

    }

    //should return CemeteryGrave object when cemetery id is specified
    @Test
    fun getCemWithGraves() = runBlockingTest {

        dao.insertCemetery(unSyncedCem)
        dao.insertCemetery(syncedCem)
        dao.insertGrave(unSyncedGrave)
        dao.insertGrave(syncedGrave)

        val cemeteryGraveActual = dao.getCemWithGraves(2).getOrAwaitValue()

        assertThat(cemeteryGraveActual.cemetery).isEqualTo(unSyncedCem)
        assertThat(cemeteryGraveActual.graves[0]).isEqualTo(unSyncedGrave)


    }


    //deletes all cemeteries with unSynced set to false
    @Test
    fun deleteUnsyncedCems() = runBlockingTest {

        dao.insertCemetery(unSyncedCem)
        dao.insertCemetery(syncedCem)
        dao.deleteUnsyncedCems(false)

        val syncedCems = dao.getAllCemeteries().asLiveData().getOrAwaitValue()

        assertThat(syncedCems.size).isEqualTo(1)
        assertThat(syncedCems[0]).isEqualTo(syncedCem)
    }


    //insertGrave returns grave id that was inserted
    @Test
    fun insertGrave() = runBlockingTest {

        dao.insertCemetery(syncedCem)
        val graveId = dao.insertGrave(syncedGrave)

        assertThat(graveId).isEqualTo(2)


    }









}
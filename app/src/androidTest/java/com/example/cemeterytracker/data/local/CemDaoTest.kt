package com.example.cemeterytracker.data.local

import androidx.test.filters.SmallTest
import com.example.cemeterytracker.data.database.CemeteryDao
import com.example.cemeterytracker.data.database.CemeteryDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
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
    private lateinit var cemetery: Cemetery

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CemeteryDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = database.dao()

        cemetery = Cemetery(
                cemeteryId = 1L,
                name = "new cemetery",
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
                county = "Chilton"
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun dao_insertCemetery_inserts_cemetery() = runBlockingTest {

        dao.insertCemetery(cemetery)
        val cemeteryToTest = dao.getCemWithId(1).getOrAwaitValue()

        assertThat(cemetery).isEqualTo(cemeteryToTest)


    }
}
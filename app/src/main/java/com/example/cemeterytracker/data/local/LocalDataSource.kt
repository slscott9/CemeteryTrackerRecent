package com.example.cemeterytracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.Update
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {


    //Cemetery
    suspend fun getNewCemeteries(): List<CemeteryGraves>


    suspend fun insertCemetery(cemetery: Cemetery) : Long

    fun getCemWithId(cemId: Long) : LiveData<Cemetery>

    fun getCemWithGraves(cemId: Long) : LiveData<CemeteryGraves>

    fun getAllCemeteries() : Flow<List<Cemetery>>

    suspend fun getUnsyncedCems() : List<CemeteryGraves>

    suspend fun insertSyncedCems(syncedCems: List<CemeteryGraves>)

    suspend fun deleteUnsyncedCems(isSynced: Boolean)

    suspend fun clearDb()


    //Update cemetery
    suspend fun getCemetery(cemId: Long) : Cemetery

    suspend fun updateCemetery(cemetery: Cemetery)





    //Grave

    suspend fun insertGrave(grave: Grave) : Long

    //get grave

    fun getGraveWithId(graveId: Long) : LiveData<Grave>




}
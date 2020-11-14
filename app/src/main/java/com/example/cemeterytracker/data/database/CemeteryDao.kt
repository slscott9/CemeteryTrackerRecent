package com.example.cemeterytracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import kotlinx.coroutines.flow.Flow

@Dao
interface CemeteryDao {

//insert cemetery

    @Insert
    suspend fun insertCemetery(cemetery: Cemetery) : Long


    @Query("select * from cemetery_tracker_table where cemeteryId = :cemId")
    fun getCemWithId(cemId: Long) : LiveData<Cemetery>

    @Transaction
    @Query("select * from cemetery_tracker_table where cemeteryId =:cemId")
    fun getCemWithGraves(cemId: Long) : LiveData<CemeteryGraves>


    @Query("select * from cemetery_tracker_table")
    fun getAllCemeteries() : Flow<List<Cemetery>>
}
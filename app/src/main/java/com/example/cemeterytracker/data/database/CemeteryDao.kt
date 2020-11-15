package com.example.cemeterytracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import kotlinx.coroutines.flow.Flow

@Dao
interface CemeteryDao {


//get cems with graves

    @Query("select * from cemetery_tracker_table where isSynced = :synced")
    suspend fun getAllCemsWithGraves(synced: Boolean) : List<CemeteryGraves>

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


//Insert Grave

    @Insert
    suspend fun insertGrave(grave: Grave) : Long

    //get  grave

    @Query("select * from grave_tracker_table where graveId =:graveId")
    fun getGraveWithId(graveId: Long) : LiveData<Grave>
}
package com.example.cemeterytracker.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import kotlinx.coroutines.flow.Flow

@Dao
interface CemeteryDao {

    //Get new cemeteries

    @Query("select * from cemetery_tracker_table where newCemetery =:newCem") //tested
    suspend fun getNewCemeteries(newCem: Boolean): List<CemeteryGraves>


//get cems with graves

    @Query("select * from cemetery_tracker_table where isSynced = :synced and newCemetery =:newCem") //tested
    suspend fun getUnsyncedCems(synced: Boolean, newCem: Boolean) : List<CemeteryGraves>

//insert cemetery

    @Insert
    suspend fun insertCemetery(cemetery: Cemetery) : Long //tested


    @Query("select * from cemetery_tracker_table where cemeteryId = :cemId") //tested
    fun getCemWithId(cemId: Long) : LiveData<Cemetery>

    //Update cemetery

    @Query("select * from cemetery_tracker_table where cemeteryId =:cemId")
    suspend fun getCemetery(cemId: Long) : Cemetery

    @Update
    suspend fun updateCemetery(cemetery: Cemetery)


    @Transaction
    @Query("select * from cemetery_tracker_table where cemeteryId =:cemId") //tested
    fun getCemWithGraves(cemId: Long) : LiveData<CemeteryGraves>


    @Query("select * from cemetery_tracker_table")
    fun getAllCemeteries() : Flow<List<Cemetery>>

    @Insert
    suspend fun insertGraveList(graveList : List<Grave>) //tested

    @Insert
    @Transaction
    suspend fun insertSyncedCems(syncedCems: List<CemeteryGraves>){
        syncedCems.forEach {
            insertCemetery(it.cemetery)
            insertGraveList(it.graves)

        }
    }

    @Query("delete from cemetery_tracker_table where isSynced =:isSynced") //tested
    suspend fun deleteUnsyncedCems(isSynced: Boolean)

    @Query("delete from cemetery_tracker_table")
    suspend fun clearDb()


//Insert Grave

    @Insert
    suspend fun insertGrave(grave: Grave) : Long //tested

    //get  grave

    @Query("select * from grave_tracker_table where graveId =:graveId") //tested
    fun getGraveWithId(graveId: Long) : LiveData<Grave>
}
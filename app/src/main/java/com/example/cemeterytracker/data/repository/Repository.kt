package com.example.cemeterytracker.data.repository

import androidx.lifecycle.LiveData
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.CemeteryResponse
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import com.example.cemeterytracker.data.dto.update.CemeteryUpdate
import com.example.cemeterytracker.other.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

interface Repository {

    suspend fun login(userRequest: UserRequest) : Resource<String>

    suspend fun register(userRequest: UserRequest) : Resource<String>


//cemetery

    suspend fun updateCemeteries( cemList : List<CemeteryUpdate>)


    suspend fun getNewCemeteries(): List<CemeteryGraves>


    suspend fun insertCemetery(cemetery: Cemetery) : Long

    fun getCemWithId(cemId: Long) : LiveData<Cemetery>

    fun getCemWithGraves(cemId: Long) : LiveData<CemeteryGraves>

    fun getAllCemeteries() : Flow<List<Cemetery>>

    suspend fun getUnsyncedCems() : List<CemeteryGraves>

    suspend fun insertSyncedCems(syncedCems: List<CemeteryGraves>)

    suspend fun deleteUnsyncedCems()

    suspend fun clearDb()


    //update cem
    suspend fun updateCemetery(cemId: Long)



    //Network Cemetery
    suspend fun sendNewCems(cemList : List<CemeteryDto>) : ServerResponse

    suspend fun getNetworkCems() : List<CemeteryResponse>


//grave

    suspend fun insertGrave(grave: Grave) : Long

    //get grave

    fun getGraveWithId(graveId: Long) : LiveData<Grave>




}
package com.example.cemeterytracker.data.repository

import androidx.lifecycle.LiveData
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.other.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun login(userRequest: UserRequest) : Resource<String>

    suspend fun register(userRequest: UserRequest) : Resource<String>


//cemetery

    suspend fun insertCemetery(cemetery: Cemetery) : Long

    fun getCemWithId(cemId: Long) : LiveData<Cemetery>

    fun getCemWithGraves(cemId: Long) : LiveData<CemeteryGraves>

    fun getAllCemeteries() : Flow<List<Cemetery>>

//grave

    suspend fun insertGrave(grave: Grave) : Long

    //get grave

    fun getGraveWithId(graveId: Long) : LiveData<Grave>




}
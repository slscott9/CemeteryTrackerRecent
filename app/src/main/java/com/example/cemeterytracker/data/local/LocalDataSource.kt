package com.example.cemeterytracker.data.local

import androidx.lifecycle.LiveData
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertCemetery(cemetery: Cemetery) : Long

    fun getCemWithId(cemId: Long) : LiveData<Cemetery>

    fun getCemWithGraves(cemId: Long) : LiveData<CemeteryGraves>

    fun getAllCemeteries() : Flow<List<Cemetery>>



}
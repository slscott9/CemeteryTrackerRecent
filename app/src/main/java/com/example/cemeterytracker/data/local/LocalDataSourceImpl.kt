package com.example.cemeterytracker.data.local

import androidx.lifecycle.LiveData
import com.example.cemeterytracker.data.database.CemeteryDao
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: CemeteryDao
) : LocalDataSource {


    //insert local cemetery
    override suspend fun insertCemetery(cemetery: Cemetery) : Long{
        return dao.insertCemetery(cemetery)
    }

    override fun getCemWithGraves(cemId: Long): LiveData<CemeteryGraves> {
        return dao.getCemWithGraves(cemId)
    }

    override fun getCemWithId(cemId: Long): LiveData<Cemetery> {
        return dao.getCemWithId(cemId)
    }

    override fun getAllCemeteries(): Flow<List<Cemetery>> {
        return dao.getAllCemeteries()
    }



    //Graves

    override suspend fun insertGrave(grave: Grave): Long {
        return dao.insertGrave(grave)
    }

    override fun getGraveWithId(graveId: Long): LiveData<Grave> {
        return dao.getGraveWithId(graveId)
    }
}
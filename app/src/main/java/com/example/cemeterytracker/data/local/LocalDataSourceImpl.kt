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

    override suspend fun getNewCemeteries(): List<CemeteryGraves> {
        return dao.getNewCemeteries(true)
    }

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

    override suspend fun getUnsyncedCems(): List<CemeteryGraves> {
        return dao.getUnsyncedCems(false, newCem = false)
    }

    override suspend fun insertSyncedCems(syncedCems: List<CemeteryGraves>) {
        dao.insertSyncedCems(syncedCems)
    }

    override suspend fun deleteUnsyncedCems(isSynced: Boolean) {
        dao.deleteUnsyncedCems(false)
    }

    override suspend fun clearDb() {
        dao.clearDb()
    }

    //Update cemetery
    override suspend fun getCemetery(cemId: Long): Cemetery {
        return dao.getCemetery(cemId)
    }

    override suspend fun updateCemetery(cemetery: Cemetery) {
        dao.updateCemetery(cemetery)
    }
    //Graves

    override suspend fun insertGrave(grave: Grave): Long {
        return dao.insertGrave(grave)
    }

    override fun getGraveWithId(graveId: Long): LiveData<Grave> {
        return dao.getGraveWithId(graveId)
    }
}
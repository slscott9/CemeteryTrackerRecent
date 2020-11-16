package com.example.cemeterytracker.data.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.CemeteryResponse
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import com.example.cemeterytracker.data.dto.update.CemeteryUpdate
import com.example.cemeterytracker.data.local.LocalDataSource
import com.example.cemeterytracker.data.remote.RemoteDataSource
import com.example.cemeterytracker.other.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val context: Application
) : Repository{

    override suspend fun login(userRequest: UserRequest): Resource<String> = withContext(Dispatchers.IO) {
        try {
            val response = remoteDataSource.login(userRequest)

            when{
                response.isSuccessful -> Resource.success("Successfully logged in")
                response.code() == 401 -> Resource.error("Invalid username or password", null)
                else -> Resource.error("Unknown error", null)
            }
        }catch (e : Exception){
            Resource.error("Check network connection", e.message)
        }
    }

    override suspend fun register(userRequest: UserRequest): Resource<String> = withContext(Dispatchers.IO){
        try {
            val response = remoteDataSource.register(userRequest)
            Resource.success(response.message)

        }catch (e: Exception){
            Resource.error(e.message ?: "Check network connection" , null)
        }
    }

    override suspend fun getNewCemeteries(): List<CemeteryGraves> {
        return localDataSource.getNewCemeteries()
    }

    override fun getCemWithId(cemId: Long): LiveData<Cemetery> {
        return localDataSource.getCemWithId(cemId)
    }

    override suspend fun insertCemetery(cemetery: Cemetery): Long {
        return localDataSource.insertCemetery(cemetery)
    }

    override fun getCemWithGraves(cemId: Long): LiveData<CemeteryGraves> {
        return localDataSource.getCemWithGraves(cemId)
    }

    override fun getAllCemeteries(): Flow<List<Cemetery>> {
        return localDataSource.getAllCemeteries()
    }

    override suspend fun getUnsyncedCems(): List<CemeteryGraves> {
        return localDataSource.getUnsyncedCems()
    }

    override suspend fun insertSyncedCems(syncedCems: List<CemeteryGraves>) {
        Timber.i("repo insert syned cems called")
        Timber.i("repo insertSyncedCems list is $syncedCems")
        localDataSource.insertSyncedCems(syncedCems)
    }

    override suspend fun deleteUnsyncedCems() {
        localDataSource.deleteUnsyncedCems(false)
    }

    override suspend fun clearDb() {
        localDataSource.clearDb()
    }

    //update cemetery

    override suspend fun updateCemeteries(cemList: List<CemeteryUpdate>) {
        Timber.i("updateCemeteries called cems to update are $cemList")
        remoteDataSource.updateCemeteries(cemList)
    }

    override suspend fun updateCemetery(cemId: Long) {
        val cemetery = localDataSource.getCemetery(cemId)
        cemetery.newCemetery = !cemetery.isSynced
        cemetery.isSynced = false
        Timber.i("cemetery.newCemetery = ${cemetery.newCemetery}")

        localDataSource.updateCemetery(cemetery)
    }

    //Network Cemetery
    override suspend fun sendNewCems(cemList: List<CemeteryDto>): ServerResponse {
        Timber.i("sendNewCems called new cems are $cemList")
        return remoteDataSource.addCems(cemList)
    }

    override suspend fun getNetworkCems(): List<CemeteryResponse> {
        return remoteDataSource.getAllCemeteries()
    }

    //Grave

    override suspend fun insertGrave(grave: Grave): Long {
        return localDataSource.insertGrave(grave)
    }


    //get grave

    override fun getGraveWithId(graveId: Long): LiveData<Grave> {
        return localDataSource.getGraveWithId(graveId)
    }
}
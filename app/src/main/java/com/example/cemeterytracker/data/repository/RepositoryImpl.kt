package com.example.cemeterytracker.data.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.local.LocalDataSource
import com.example.cemeterytracker.data.remote.RemoteDataSource
import com.example.cemeterytracker.other.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
}
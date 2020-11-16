package com.example.cemeterytracker.data.remote

import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.CemeteryResponse
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import com.example.cemeterytracker.data.dto.update.CemeteryUpdate
import com.example.cemeterytracker.network.CemeteryApi
import com.example.cemeterytracker.network.SafeApiRequest
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val cemeteryApi: CemeteryApi
) : RemoteDataSource, SafeApiRequest(){

    override suspend fun login(userRequest: UserRequest): Response<ResponseBody> {
        return cemeteryApi.login(userRequest)
    }

    override suspend fun register(userRequest: UserRequest): ServerResponse =
        apiRequest { cemeteryApi.register(userRequest) }

    override suspend fun addCems(cemList: List<CemeteryDto>)  =
            apiRequest { cemeteryApi.addCems(cemList) }

    override suspend fun getAllCemeteries(): List<CemeteryResponse> =
            apiRequest { cemeteryApi.getAllCemeteries() }

    override suspend fun updateCemeteries(cemList: List<CemeteryUpdate>): ServerResponse =
            apiRequest { cemeteryApi.updateCemeteries(cemList) }
}
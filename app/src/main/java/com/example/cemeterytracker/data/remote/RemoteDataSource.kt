package com.example.cemeterytracker.data.remote

import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.CemeteryResponse
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import com.example.cemeterytracker.data.dto.update.CemeteryUpdate
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

interface RemoteDataSource {

    suspend fun login(userRequest: UserRequest) : Response<ResponseBody>

    suspend fun register(userRequest: UserRequest) : ServerResponse


    suspend fun addCems(cemList : List<CemeteryDto>) : ServerResponse

    suspend fun getAllCemeteries() : List<CemeteryResponse>

    suspend fun updateCemeteries( cemList : List<CemeteryUpdate>) : ServerResponse




}
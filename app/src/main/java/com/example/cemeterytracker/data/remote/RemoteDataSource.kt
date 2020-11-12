package com.example.cemeterytracker.data.remote

import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body

interface RemoteDataSource {

    suspend fun login(userRequest: UserRequest) : Response<ResponseBody>

    suspend fun register(userRequest: UserRequest) : ServerResponse

}
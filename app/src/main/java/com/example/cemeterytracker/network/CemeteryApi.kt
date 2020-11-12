package com.example.cemeterytracker.network

import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CemeteryApi {

    @POST("/login")
    suspend fun login(@Body userRequest: UserRequest) : Response<ResponseBody>

    @POST("/register")
    suspend fun register(@Body userRequest: UserRequest) : Response<ServerResponse>


}
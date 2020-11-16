package com.example.cemeterytracker.network

import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.dto.responses.CemeteryResponse
import com.example.cemeterytracker.data.dto.responses.ServerResponse
import com.example.cemeterytracker.data.dto.update.CemeteryUpdate
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



interface CemeteryApi {

    @POST("/login")
    suspend fun login(@Body userRequest: UserRequest) : Response<ResponseBody>

    @POST("/register")
    suspend fun register(@Body userRequest: UserRequest) : Response<ServerResponse>

    @GET("/user/cemeteries")
    suspend fun getAllCemeteries() : Response<List<CemeteryResponse>>

    @POST("/user/add/cemeteries")
    suspend fun addCems(@Body cemList : List<CemeteryDto>) : Response<ServerResponse>


    @POST("/user/update/cemeteries")
    suspend fun updateCemeteries(@Body cemList : List<CemeteryUpdate>) : Response<ServerResponse>


}
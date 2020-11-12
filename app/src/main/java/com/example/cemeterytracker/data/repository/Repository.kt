package com.example.cemeterytracker.data.repository

import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.other.Resource

interface Repository {

    suspend fun login(userRequest: UserRequest) : Resource<String>

    suspend fun register(userRequest: UserRequest) : Resource<String>
}
package com.example.cemeterytracker.data.dto

data class UserRequest(
    val userName:String,
    val email: String,
    val password: String,
    val gravesAdded: Int,
    val cemeteriesAdded: Int
)
package com.example.cemeterytracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var gravesAdded: Int,
    var cemeteriesAdded: Int
)
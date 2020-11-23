package com.example.cemeterytracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.database.entities.User

@Database(entities = [Cemetery::class, Grave::class, User::class], version = 10)
abstract class CemeteryDatabase: RoomDatabase() {
    abstract fun dao() : CemeteryDao
}
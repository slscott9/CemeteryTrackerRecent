package com.example.cemeterytracker.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "grave_tracker_table", foreignKeys = arrayOf(
    ForeignKey(entity = Cemetery::class,
    parentColumns = arrayOf("cemeteryId"),
    childColumns = arrayOf("cemeteryId"),
            onDelete = ForeignKey.CASCADE

    )
))
data class Grave(

    @PrimaryKey(autoGenerate = true)
    val graveId: Long,

    val cemeteryId: Long,

    val firstName: String,

    val lastName: String,

    val birthDate: String,

    val deathDate: String,

    val marriageYear: String,

    val comment: String,

    val graveNumber: String,

    var isSynced: Boolean = false,

    val addedBy: String
)
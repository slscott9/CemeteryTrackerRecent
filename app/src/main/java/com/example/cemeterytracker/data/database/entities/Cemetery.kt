package com.example.cemeterytracker.data.database.entities

import androidx.room.*
import com.google.gson.annotations.Expose
import java.util.*

@Entity(tableName = "cemetery_tracker_table")

data class
Cemetery(

    @PrimaryKey(autoGenerate = true)
    val cemeteryId: Long = 0,

    val name: String,

    val location: String,

    val state: String,

    val county: String,

    val township: String,

    val range: String,

    val spot: String,

    val firstYear: String,

    val section: String,

    var isSynced: Boolean = false,

    val graveCount: Int,

    val addedBy: String



)

data class CemeteryGraves(
    @Embedded val cemetery: Cemetery,
    @Relation(
        parentColumn = "cemeteryId",
        entityColumn = "cemeteryId"
    )
    val graves: List<Grave>
)
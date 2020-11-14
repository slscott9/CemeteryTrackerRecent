package com.example.cemeterytracker.data.domain

import com.example.cemeterytracker.data.database.entities.Cemetery

data class DomainCemetery(
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

fun List<Cemetery>.asDomainCemList() : List<DomainCemetery> {
    return map {
        DomainCemetery(
            cemeteryId = it.cemeteryId,
            name = it.name,
            location = it.location,
            state = it.state,
            county = it.county,
            township = it.township,
            range = it.range,
            spot = it.spot,
            firstYear = it.firstYear,
            section = it.section,
            isSynced = it.isSynced,
            graveCount = it.graveCount,
            addedBy = it.addedBy
        )
    }
}
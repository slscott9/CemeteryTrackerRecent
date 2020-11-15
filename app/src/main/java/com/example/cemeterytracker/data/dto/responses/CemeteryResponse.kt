package com.example.cemeterytracker.data.dto.responses

import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.GraveDto
import com.example.cemeterytracker.data.dto.asNetworkGraves

data class CemeteryResponse(
    val cemeteryId: Long,
    val name: String,
    val location: String,
    val state: String,
    val county: String,
    val township: String,
    val range: String,
    val spot: String,
    val firstYear: String,
    val section: String,
    var synced: Boolean,
    val graveCount: Int,
    val addedBy: String,
    val graves: List<GraveResponse>

)

data class GraveResponse(
    val graveId: Long,
    val cemeteryId: Long,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val deathDate: String,
    val marriageYear: String,
    val comment: String,
    val graveNumber: String,
    var synched: Boolean,
    val addedBy: String
)

fun CemeteryResponse.asDatabaseCemetery() : Cemetery {
    return Cemetery(
        name = name,
        cemeteryId = cemeteryId,
        location = location,
        state = state,
        county = county,
        township = township,
        range = range,
        spot = spot,
        firstYear = firstYear,
        section = section,
        isSynced = synced,
        graveCount = graveCount,
        addedBy = addedBy
    )
}

fun List<CemeteryResponse>.asDataBaseModel() : List<CemeteryGraves> {
    return map {
        CemeteryGraves(
            cemetery = it.asDatabaseCemetery(),
            graves = it.graves.asDatabaseGraves()
        )
    }
}



fun List<GraveResponse>.asDatabaseGraves() : List<Grave>{
    return map {
        Grave(
            graveId = it.graveId,
            cemeteryId = it.cemeteryId,
            firstName = it.firstName,
            lastName = it.lastName,
            birthDate = it.birthDate,
            deathDate = it.deathDate,
            marriageYear = it.marriageYear,
            comment = it.comment,
            graveNumber = it.graveNumber,
            isSynced = it.synched,
            addedBy = it.addedBy
        )
    }
}

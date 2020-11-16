package com.example.cemeterytracker.data.dto.responses

import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.dto.CemeteryDto
import com.example.cemeterytracker.data.dto.GraveDto
import com.example.cemeterytracker.data.dto.asNetworkGraves

data class CemeteryResponse(
    val id: Long,
    val name: String,
    val location: String,
    val state: String,
    val county: String,
    val townShip: String,
    val range: String,
    val spot: String,
    val firstYear: String,
    val section: String,
    var synced: Boolean,
    val graveCount: Int,
    val addedBy: String,
    val graves: List<GraveResponse>

)

fun List<CemeteryGraves>.asCemeterResponses() : List<CemeteryResponse>{
    return map {
        CemeteryResponse(
                id = it.cemetery.cemeteryId,
                name = it.cemetery.name,
                location = it.cemetery.location,
                state = it.cemetery.state,
                county = it.cemetery.county,
                townShip = it.cemetery.township,
                range = it.cemetery.range,
                spot = it.cemetery.spot,
                firstYear = it.cemetery.firstYear,
                section = it.cemetery.section,
                synced = it.cemetery.isSynced,
                graveCount = it.cemetery.graveCount,
                addedBy = it.cemetery.addedBy,
                graves = it.graves.asGraveResponses()

                )
    }
}

fun List<Grave>.asGraveResponses() : List<GraveResponse>{
    return map {
        GraveResponse(
                graveId = it.graveId,
                cemetery = it.cemeteryId,
                firstName = it.firstName,
                lastName = it.lastName,
                birthDate = it.birthDate,
                deathDate = it.deathDate,
                marriageYear = it.marriageYear,
                comment = it.comment,
                graveNumber = it.graveNumber,
                synced = it.isSynced,
                addedBy = it.addedBy
        )
    }
}

data class GraveResponse(
    val graveId: Long,
    val cemetery: Long,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val deathDate: String,
    val marriageYear: String,
    val comment: String,
    val graveNumber: String,
    var synced: Boolean,
    val addedBy: String
)

fun CemeteryResponse.asDatabaseCemetery() : Cemetery {
    return Cemetery(
        name = name,
        cemeteryId = id,
        location = location,
        state = state,
        county = county,
        township = townShip,
        range = range,
        spot = spot,
        firstYear = firstYear,
        section = section,
        isSynced = synced,
        graveCount = graveCount,
        addedBy = addedBy,
            newCemetery = false
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
            cemeteryId = it.cemetery,
            firstName = it.firstName,
            lastName = it.lastName,
            birthDate = it.birthDate,
            deathDate = it.deathDate,
            marriageYear = it.marriageYear,
            comment = it.comment,
            graveNumber = it.graveNumber,
            isSynced = it.synced,
            addedBy = it.addedBy
        )
    }
}

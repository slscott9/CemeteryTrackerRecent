package com.example.cemeterytracker.data.dto.update

import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.dto.responses.CemeteryResponse
import com.example.cemeterytracker.data.dto.responses.GraveResponse

/*
    This dto has to be used for updates why?

    Server grave object has cemetery property that is a Cemetery server object that must be sets for writes only.

    Local Graves have a cemetery property that is a long representing the cemetery's id that it belongs to.

    this dto excludes the cemetery property when making the update network call

    Server side will then set the server graves cemetery property
 */

data class CemeteryUpdate(
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
        val graves: List<GraveUpdate>

)

data class GraveUpdate(
        val graveId: Long,
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



fun List<CemeteryGraves>.asCemeteryUpdates() : List<CemeteryUpdate>{
    return map {
        CemeteryUpdate(
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
                graves = it.graves.asGraveUpdates()

        )
    }
}

fun List<Grave>.asGraveUpdates() : List<GraveUpdate>{
    return map {
        GraveUpdate(
                graveId = it.graveId,
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
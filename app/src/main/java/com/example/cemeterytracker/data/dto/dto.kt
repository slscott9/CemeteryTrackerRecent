package com.example.cemeterytracker.data.dto

import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.CemeteryGraves
import com.example.cemeterytracker.data.database.entities.Grave

data class UserRequest(
    val userName:String,
    val email: String,
    val password: String,
    val gravesAdded: Int,
    val cemeteriesAdded: Int
)


data class CemeteryDto(
    val name: String,
    val location: String,
    val state: String,
    val county: String,
    val township: String,
    val cemRange: String,
    val spot: String,
    val firstYear: String,
    val cemSection: String,
    var isSynced: Boolean,
    val graveCount: Int,
    val addedBy: String,
    val graves: List<GraveDto>

)

data class GraveDto(
    val parentCemeteryId: Long,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val deathDate: String,
    val marriageYear: String,
    val comment: String,
    val graveNumber: String,
    var isSynced: Boolean,
    val addedBy: String
)

fun List<CemeteryGraves>.asNetworkCemetery() : List<CemeteryDto> {
    return map {
        CemeteryDto(
            name = it.cemetery.name,
            location = it.cemetery.location,
            state = it.cemetery.state,
            county = it.cemetery.county,
            township = it.cemetery.township,
            cemRange = it.cemetery.range,
            spot = it.cemetery.spot,
            firstYear = it.cemetery.firstYear,
            cemSection = it.cemetery.section,
            isSynced = it.cemetery.isSynced,
            graveCount = it.cemetery.graveCount,
            addedBy = it.cemetery.addedBy,
            graves = it.graves.asNetworkGraves()
        )
    }
}

fun List<Grave>.asNetworkGraves() : List<GraveDto> {
    return map {
        GraveDto(
            parentCemeteryId = it.cemeteryId,
            firstName = it.firstName,
            lastName = it.lastName,
            birthDate = it.birthDate,
            deathDate = it.deathDate,
            marriageYear = it.marriageYear,
            comment = it.comment,
            graveNumber = it.graveNumber,
            isSynced = it.isSynced,
            addedBy = it.addedBy
        )
    }
}
package com.example.cemeterytracker.data.domain

import com.example.cemeterytracker.data.database.entities.Grave

data class DomainGrave(
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

fun List<Grave>.asDomainGraveList() : List<DomainGrave>{
    return map {
        DomainGrave(
            graveId = it.graveId,
            cemeteryId = it.cemeteryId,
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
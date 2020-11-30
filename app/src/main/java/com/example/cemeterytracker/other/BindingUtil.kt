package com.example.cemeterytracker.other

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.domain.DomainCemetery
import com.example.cemeterytracker.data.domain.DomainGrave

@BindingAdapter("setGraveName")
fun TextView.setGraveName(item: DomainGrave?){
    item?.let { text = "${item.firstName} ${item.lastName}" }
}


@BindingAdapter("setCemeteryName")
fun TextView.setCemeteryName(domainCemetery: DomainCemetery?){
    domainCemetery?.name?.let { text = domainCemetery.name }
}

@BindingAdapter("setCemeteryLocation")
fun TextView.setCemeteryLocation(domainCemetery: DomainCemetery?){
    domainCemetery?.location?.let { text = domainCemetery.location }
}


@BindingAdapter("setEtGraveFirstName")
fun EditText.setEtGraveFirstName(grave: Grave?){
    grave?.firstName?.let { setText(grave.firstName) }
}


@BindingAdapter("setEtGraveLast")
fun EditText.setEtGraveLast(grave: Grave?){
    grave?.lastName?.let { setText(grave.lastName) }
}


@BindingAdapter("setEtGraveBirth")
fun EditText.setEtGraveBirth(grave: Grave?){
    grave?.lastName?.let { setText(grave.lastName) }
}

@BindingAdapter("setEtGraveDeath")
fun EditText.setEtGraveDeath(grave: Grave?){
    grave?.deathDate?.let { setText(grave.deathDate) }
}



@BindingAdapter("setEtGraveMarried")
fun EditText.setEtGraveMarried(grave: Grave?){
    grave?.marriageYear?.let { setText(grave.marriageYear) }
}

@BindingAdapter("setEtGraveNum")
fun EditText.setEtGraveNum(grave: Grave?){
    grave?.graveNumber?.let { setText(grave.graveNumber) }
}


@BindingAdapter("setEtGraveComment")
fun EditText.setEtGraveComment(grave: Grave?){
    grave?.comment?.let { setText(grave.comment) }
}


@BindingAdapter("setGraveName")
fun TextView.setGraveName(grave: Grave?){
    grave?.let { text = "${grave.firstName} ${grave.lastName} " }
}

@BindingAdapter("setGraveBirth")
fun TextView.setGraveBirth(grave: Grave?){
    grave?.birthDate?.let { text = grave.birthDate }
}

@BindingAdapter("setGraveDeath")
fun TextView.setGraveDeath(grave: Grave?){
    grave?.deathDate?.let { text = grave.deathDate }
}

@BindingAdapter("setGraveMarried")
fun TextView.setGraveMarried(grave: Grave?){
    grave?.marriageYear?.let { text = grave.marriageYear }
}

@BindingAdapter("setGraveComment")
fun TextView.setGraveComment(grave: Grave?){
    grave?.comment?.let { text = grave.comment }
}

@BindingAdapter("setGraveNum")
fun TextView.setGraveNum(grave: Grave?){
    grave?.graveNumber?.let { text = grave.graveNumber }
}

@BindingAdapter("setAddedBy")
fun TextView.setAddedBy(cemetery: Cemetery?){
    cemetery?.addedBy?.let{
        text = "Added by: ${cemetery.addedBy}"
    }
}








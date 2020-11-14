package com.example.cemeterytracker.other

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
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


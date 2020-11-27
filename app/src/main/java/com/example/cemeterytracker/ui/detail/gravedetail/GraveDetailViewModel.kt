package com.example.cemeterytracker.ui.detail.gravedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.cemeterytracker.data.repository.Repository

class GraveDetailViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(){


    private val _graveId = MutableLiveData<Long>()

    val graveSelected = _graveId.switchMap{
        repository.getGraveWithId(it)
    }

    fun setGraveId(graveId : Long ){
        _graveId.value = graveId
    }
}


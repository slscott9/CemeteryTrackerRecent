package com.example.cemeterytracker.ui.detail.cemdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.cemeterytracker.data.repository.Repository

class CemDetailViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {


    private val _cemId = MutableLiveData<Long>()
    val cemId : LiveData<Long> = _cemId

    val cemeteryWithGraves = _cemId.switchMap {
        repository.getCemWithGraves(it)
    }



    fun setCemId(cemId : Long) {
        _cemId.value = cemId
    }


}
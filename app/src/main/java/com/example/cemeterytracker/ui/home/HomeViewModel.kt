package com.example.cemeterytracker.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.repository.Repository

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _allCems = repository.getAllCemeteries().asLiveData(viewModelScope.coroutineContext)

    val allCems = _allCems


}
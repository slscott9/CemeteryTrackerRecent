package com.example.cemeterytracker.ui.addedit.cemetery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.repository.Repository
import kotlinx.coroutines.launch

class AddEditCemViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel(){


        private val _cemId = MutableLiveData<Long>()
        val cemId: LiveData<Long> = _cemId


        fun insertCemetery(cemetery: Cemetery) {

                viewModelScope.launch {
                        _cemId.value = repository.insertCemetery(cemetery)
                }


        }
}
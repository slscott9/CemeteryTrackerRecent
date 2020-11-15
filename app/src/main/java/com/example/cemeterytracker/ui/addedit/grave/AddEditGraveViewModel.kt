package com.example.cemeterytracker.ui.addedit.grave

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cemeterytracker.data.database.entities.Grave
import com.example.cemeterytracker.data.repository.Repository
import kotlinx.coroutines.launch

class AddEditGraveViewModel @ViewModelInject constructor(
        private val repository: Repository
) : ViewModel(){


    private val _graveId = MutableLiveData<Long>()
    val graveId : LiveData<Long> = _graveId

    private val _graveInsertStatus = MutableLiveData<Boolean>()
    val graveInsertSuccess : LiveData<Boolean> = _graveInsertStatus

    private val _graveToEdit = _graveId.switchMap {
        repository.getGraveWithId(it)
    }

    val graveToEdit = _graveToEdit


    fun setGraveId(graveId: Long) {
        _graveId.value = graveId
    }

    fun insertGrave(grave: Grave){

        viewModelScope.launch {
            repository.insertGrave(grave)

            _graveInsertStatus.postValue(true)


        }
    }


}
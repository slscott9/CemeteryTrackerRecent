package com.example.cemeterytracker.ui.login.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.other.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class RegisterViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(){


    private val _registerStatus = MutableLiveData<Resource<String>>()
    val registerStatus: LiveData<Resource<String>> = _registerStatus

    fun register(userEmail: String, userName: String, password : String) {

        Timber.i("username is $userName, password is $password, email is $userEmail")

        _registerStatus.postValue(Resource.loading(null))

        if(userEmail.isEmpty() || password.isEmpty() || userName.isEmpty()){
            _registerStatus.postValue(Resource.error("Please fill in all fields", null))
            return
        }

        viewModelScope.launch {
            val resource = repository.register(
                UserRequest(
                    userName = userName,
                    email = userEmail,
                    password = password,
                    gravesAdded = 0,
                    cemeteriesAdded = 0
                )
            )

            _registerStatus.postValue(resource)
        }
    }
}
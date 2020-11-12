package com.example.cemeterytracker.ui.login.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cemeterytracker.data.dto.UserRequest
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.data.repository.RepositoryImpl
import com.example.cemeterytracker.other.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(){


    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus : LiveData<Resource<String>> = _loginStatus

    fun login(userName: String, password: String){

        _loginStatus.postValue(Resource.loading(null))

        if(userName.isEmpty() || password.isEmpty()){
            _loginStatus.postValue(Resource.error("Please fill out all fields", null))
            return
        }

        viewModelScope.launch {
            val resource = repository.login(
                UserRequest(
                    email = "",
                    userName = userName,
                    password = password,
                    gravesAdded = 0,
                    cemeteriesAdded = 0
                )
            )

            _loginStatus.postValue(resource)

        }
    }

}
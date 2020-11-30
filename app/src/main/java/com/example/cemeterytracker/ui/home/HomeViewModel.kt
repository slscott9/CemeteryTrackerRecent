package com.example.cemeterytracker.ui.home

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.cemeterytracker.data.database.entities.Cemetery
import com.example.cemeterytracker.data.repository.Repository
import com.example.cemeterytracker.other.Constants
import java.security.PrivateKey

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val userName = sharedPreferences.getString(Constants.KEY_LOGGED_IN_USERNAME, Constants.NO_USERNAME)

    private val _allCems = repository.getAllCemeteries().asLiveData(viewModelScope.coroutineContext)

    val allCems = _allCems

    private val _userAddedCemList = repository.getCemsAddedByUser(userName ?: "")
    val userAddedCemList : LiveData<List<Cemetery>> = _userAddedCemList




}
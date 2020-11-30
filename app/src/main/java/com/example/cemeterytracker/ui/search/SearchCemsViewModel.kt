package com.example.cemeterytracker.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cemeterytracker.data.repository.Repository
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest

class SearchCemsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(){

    //ConflatedBroadCaseChannel sends only most recent element to all subscribers

    private val searchChanel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(searchQuery: String){
        searchChanel.offer(searchQuery) //offer sends searchQuery to all subscribers
    }

    val cemeterySearchList = searchChanel.asFlow()

            //flatMaplatest since user might enter a new searchQuery before original flow is done emitting

        .flatMapLatest { searchQuery -> repository.getSearchedCemsList(searchQuery) }.asLiveData()

}
package com.photon.findmyip.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photon.findmyip.models.IpLocation
import com.photon.findmyip.repository.IpRepository
import kotlinx.coroutines.launch
import com.photon.findmyip.models.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class IPViewModel @Inject constructor(private val ipRepository : IpRepository) : ViewModel(){

    private val _ipList = MutableLiveData<Result<IpLocation>>()
    val ipList : LiveData<Result<IpLocation>> = _ipList
    val loading =  mutableStateOf(false)

    init{
        fetchIpData()
    }

    private fun fetchIpData(){

        viewModelScope.launch {
            loading.value = true
            delay(2000)
            ipRepository.getIpDetails().collect {
                _ipList.value = it
                loading.value = false
            }
        }
    }
}
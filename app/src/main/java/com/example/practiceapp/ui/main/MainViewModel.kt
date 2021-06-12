package com.example.practiceapp.ui.main

import android.util.Log
import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceapp.repository.MainRepository
import com.skydoves.whatif.whatIf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _toastMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: LiveData<String> get() = _toastMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _usersList = MutableLiveData<List<String>>()
    val usersList : LiveData<List<String>> = _usersList

    private val usersFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    private val usersListFlow = usersFetchingIndex.flatMapLatest { page ->
        Log.d("ddd", "MainViewModel usersFetchingIndex page = $page")
        mainRepository.fetchUsers(
            onStart = { _isLoading.postValue(true) },
            onComplete = { _isLoading.postValue(false) },
            onError = { _toastMessage.postValue(it) }
        )
    }
    init {
        Log.d("ddd", "MainViewModel init")
        viewModelScope.launch {
            usersListFlow.collect {
                _usersList.value = it
            }
        }
    }

    @MainThread
    fun fetchUsers() {
        whatIf(!_isLoading.value!!) {
            _usersList.value = emptyList()
            usersFetchingIndex.value++
        }
    }
}
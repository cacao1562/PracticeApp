package com.example.practiceapp.repository

import androidx.annotation.WorkerThread
import com.example.practiceapp.api.ApiService
import com.skydoves.sandwich.*
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: ApiService
) {

    @WorkerThread
    fun fetchUsers(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = service.fetchUsers()
        response.suspendOnSuccess {
            data.whatIfNotNull { response ->
                val users = response.users
                emit(users)
            }
        }.onError {
            onError("[Code: ${statusCode.code}]: ${message()}")
        }.onException {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchUsersInfo(
        page : Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = service.fetchUsersInfo(page)
        response.suspendOnSuccess {
            data.whatIfNotNull { response ->
                val users = response.users
                emit(users)
            }
        }.onError {
            onError("[Code: ${statusCode.code}]: ${message()}")
        }.onException {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}
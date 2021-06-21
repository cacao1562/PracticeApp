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
    fun fetchMapInfo(
        cid : String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val response = service.fetchMapInfo(cid)
        response.suspendOnSuccess {
            data.whatIfNotNull { response ->
                val s2graph = response.s2graph
                emit(s2graph)
            }
        }.onError {
            onError("[Code: ${statusCode.code}]: ${message()}")
        }.onException {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}
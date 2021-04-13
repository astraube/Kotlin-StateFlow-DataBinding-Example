package com.github.astraube.stateflowbinding.data.repository

import com.github.astraube.stateflowbinding.data.source.remove.ApiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository {

    private val _apiProvider = ApiProvider()

    fun loadData() = flow {
        emit(_apiProvider.getFromRemote())
    }

    fun addData(value: String?) = flow {
        emit(_apiProvider.addToRemote(value))
    }
}
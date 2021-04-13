package com.github.astraube.stateflowbinding.ui.names

import android.view.View
import androidx.lifecycle.*
import com.github.astraube.stateflowbinding.common.extensions.hideKeyboard
import com.github.astraube.stateflowbinding.data.repository.MainRepository
import com.github.astraube.stateflowbinding.data.source.ResultSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NamesViewModel constructor(private val mainRepo: MainRepository) : ViewModel(), LifecycleObserver {

    private val _uiState: MutableStateFlow<ResultSource<Any>> = MutableStateFlow(ResultSource.Default)
    val uiState: LiveData<ResultSource<Any>> = _uiState.asLiveData()

    private val _dataList: MutableStateFlow<String> = MutableStateFlow("")
    val dataList: LiveData<String> = _dataList.asLiveData()

    val newName: MutableLiveData<String> = MutableLiveData("")

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEventStart() {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepo.loadData()
                .onStart {
                    _uiState.value = ResultSource.Loading

                    // Delay 3s to view the loading animation
                    delay(3000)
                }
                .onEmpty {
                    _uiState.value = ResultSource.Empty()
                }
                .catch { e ->
                    _uiState.value = ResultSource.Failure(message = e.message, throwable = e)

                    e.printStackTrace()
                }
                .flowOn(Dispatchers.IO)
                .collect { listFromDataSource ->
                    _dataList.value = listFromDataSource.joinToString("\n")

                    _uiState.value = ResultSource.Success(code = 200, data = listFromDataSource)
                }
        }
    }

    fun addData(view: View) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepo.addData(newName.value)
                    .onStart {
                        view.context.hideKeyboard(view)

                        newName.postValue("")

                        _uiState.value = ResultSource.Loading

                        // Delay 1s to view the loading animation
                        delay(1000)
                    }
                    .onEmpty {
                        _uiState.value = ResultSource.Empty()
                    }
                    .catch { e ->
                        _uiState.value = ResultSource.Failure(message = e.message, throwable = e)

                        //e.printStackTrace()

                        emit(false)
                    }
                    .flowOn(Dispatchers.IO)
                    .collect { result ->
                        if (result) {
                            _uiState.value = ResultSource.Success(code = 200, data = result)

                            loadData()
                        }
                    }
        }
    }
}
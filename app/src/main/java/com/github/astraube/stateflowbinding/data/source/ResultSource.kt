package com.github.astraube.stateflowbinding.data.source

import androidx.lifecycle.MutableLiveData
import java.io.Serializable

/**
 * Created on 13/04/2021
 * @author André Straube
 *
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultSource<out T>: Serializable {
    data class Success<T>(val code: Int, val data: T?, val message: String? = null) : ResultSource<T>()
    data class Failure(val code: Int? = null, val message: String? = null, val throwable: Throwable? = null) : ResultSource<Nothing>()
    data class Empty(val code: Int? = null, val message: String? = null) : ResultSource<Nothing>()
    object NetworkError : ResultSource<Nothing>()
    object Loading : ResultSource<Nothing>()
    object Default : ResultSource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[code=$code, data=$data, message=$message]"
            is Failure -> "Failure[code=$code, message=$message, message=$throwable]"
            is Empty -> "Empty[code=$code, message=$message]"
            NetworkError -> "NetworkError"
            Loading -> "Loading"
            Default -> "Default"
        }
    }
}

val <T> ResultSource<T>.data: T?
    get() = (this as? ResultSource.Success)?.data

val ResultSource<*>.isFailure
    get() = this is ResultSource.Failure

val ResultSource<*>.isLoading
    get() = this is ResultSource.Loading

val ResultSource<*>.isEmpty
    get() = this is ResultSource.Empty

val ResultSource<*>.isNetworkError
    get() = this is ResultSource.NetworkError

val ResultSource<*>.isSuccessful
    get() = when (this) {
        is ResultSource.Success<*> -> this.code in 200 until 300
        is ResultSource.Failure,
        is ResultSource.Loading,
        is ResultSource.Default,
        is ResultSource.NetworkError,
        is ResultSource.Empty -> false
    }


val ResultSource<*>.statusMessage
    get() = when (this) {
        is ResultSource.Success<*> -> this.message
        is ResultSource.Failure -> this.message
        is ResultSource.Empty -> this.message
        is ResultSource.NetworkError,
        is ResultSource.Loading,
        is ResultSource.Default -> ""
    }

val ResultSource<*>.statusCode: Int?
    get() = when (this) {
        is ResultSource.Success<*> -> this.code
        is ResultSource.Failure -> this.code
        is ResultSource.Empty -> this.code
        is ResultSource.NetworkError,
        is ResultSource.Loading,
        is ResultSource.Default -> null
    }

/**
 * `true` if [ResultSource] is of type [Success] & holds non-null [Success.data].
 */
val ResultSource<*>.succeeded
    get() = this is ResultSource.Success && data != null

fun <T> ResultSource<T>.successOr(fallback: T): T {
    return (this as? ResultSource.Success<T>)?.data ?: fallback
}

/**
 * Updates value of [liveData] if [ResultSource] is of type [Success]
 */
inline fun <reified T> ResultSource<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
    if (this is ResultSource.Success) {
        liveData.value = data
    }
}

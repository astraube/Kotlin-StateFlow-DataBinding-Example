package com.github.astraube.stateflowbinding.data.source.remove

import java.lang.Exception

/**
 * Created on 12/04/2021
 * @author André Straube
 */
class ApiProvider {

    private val _listNames: MutableList<String> = mutableListOf("Andre", "Anderson", "Ana", "Arnalda", "Adil")

    fun getFromRemote(): List<String> = _listNames.toList()

    fun addToRemote(value: String?): Boolean {
        // Simulate Exception
        if (_listNames.size % 2 == 0)
            throw Exception("Oooops. Couldn't add item")

        if (!value.isNullOrBlank()) {
            _listNames.add(value)

            return true
        }
        return false
    }
}
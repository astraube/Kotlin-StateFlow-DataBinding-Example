package com.github.astraube.stateflowbinding.data.enums

/**
 * Created on 12/04/2021
 * @author André Straube
 */
enum class Status(val id: Int) {
    DEFAULT(0),
    SUCCESS(1),
    ERROR(2),
    LOADING(3),
    EMPTY(4)
}
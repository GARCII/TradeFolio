package com.portfolio.tracker.util


data class Resource<out T>(val status: Status, val data: T?, val message: String? = null) {

    enum class Status {
        SUCCESS,
        ERROR
    }
}


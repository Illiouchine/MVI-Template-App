package com.illiouchine.mviapplication.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepository {

    var shouldFail: Boolean = false

    fun loadData(): Flow<List<String>> = flow {
        delay(1000)
        if (shouldFail) {
            shouldFail = false
            throw Exception("Hohoho")
        } else {
            shouldFail = true
            emit(listOf("one", "two", "tree"))
        }
    }
}
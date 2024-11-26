package com.amanullah.assessment.base.failure.manager

import android.content.Context
import com.amanullah.assessment.base.failure.Failure
import javax.inject.Inject

class FailureManagerImpl @Inject constructor(private val context: Context) : FailureManager {
    override fun handleFailure(failure: Failure): String = when (failure) {
        is Failure.ActualException -> failure.exception.message.toString()
        is Failure.HTTP.BadRequest -> "Bad request"
        is Failure.HTTP.CanNotConnectToTheServer -> "Can't connect to the server"
        is Failure.LocalCache.FailedToCache -> "Failed to cache"
        is Failure.HTTP.Forbidden -> "Forbidden request"
        is Failure.HTTP.InternalServerError -> "Internal server error"
        is Failure.HTTP.MethodNotAllowed -> "Method not allowed"
        is Failure.HTTP.NetworkConnection -> "No internet available"
        is Failure.LocalCache.NotExistInCache -> "Not exist in cache"
        is Failure.HTTP.NotFound -> "Not found"
        is Failure.HTTP.TooManyRequest -> "Too many request"
        is Failure.HTTP.UnauthorizedError -> "unauthorized error"
    }
}
package com.marcelo.marvelheroes.extensions

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal fun Throwable.isConnectionError() =
    this is UnknownHostException || cause is UnknownHostException ||
            this is SocketTimeoutException || cause is SocketTimeoutException

internal fun Throwable.isServerError() =
    this is HttpException && code() == 500

package com.marcelo.marvelheroes.extensions

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val STATUS_500 = 500
private const val STATUS_600 = 600

internal fun Throwable.isConnectionError() =
    this is UnknownHostException || cause is UnknownHostException ||
            this is SocketTimeoutException || cause is SocketTimeoutException

internal fun Throwable.isServerError(): Boolean {
    return this is HttpException && code() >= STATUS_500 && code() <= STATUS_600
}

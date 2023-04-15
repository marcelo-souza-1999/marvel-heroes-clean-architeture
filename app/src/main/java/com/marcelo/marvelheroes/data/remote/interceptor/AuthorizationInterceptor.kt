package com.marcelo.marvelheroes.data.remote.interceptor

import com.marcelo.marvelheroes.extensions.toMd5
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Interceptor para adicionar os parâmetros de autorização nas requisições da API da Marvel.
 * @param publicKey Chave pública da API da Marvel.
 * @param privateKey Chave privada da API da Marvel.
 */
class AuthorizationInterceptor(
    val publicKey: String,
    val privateKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestUrl = request.url

        require(publicKey.isNotBlank())
        require(privateKey.isNotBlank())

        val calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE_UTC))
        val timeStamp = (calendar.timeInMillis / THOUSAND).toString()
        val hash = "$timeStamp$privateKey$publicKey".toMd5()
        val newUrl = requestUrl.newBuilder()
            .addQueryParameter(QUERY_PARAMETER_TIME_STAMP, timeStamp)
            .addQueryParameter(QUERY_PARAMETER_API_KEY, publicKey)
            .addQueryParameter(QUERY_PARAMETER_HASH, hash)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    companion object {
        private const val QUERY_PARAMETER_TIME_STAMP = "ts"
        private const val QUERY_PARAMETER_API_KEY = "apikey"
        private const val QUERY_PARAMETER_HASH = "hash"
        private const val TIME_ZONE_UTC = "UTC"
        private const val THOUSAND = 1000L
    }
}

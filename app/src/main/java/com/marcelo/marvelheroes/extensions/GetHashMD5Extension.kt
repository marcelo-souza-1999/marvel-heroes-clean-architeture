package com.marcelo.marvelheroes.extensions

import java.math.BigInteger
import java.security.MessageDigest

private const val MD5 = "MD5"
private const val ZERO = '0'
private const val ONE = 1
private const val SIXTEEN = 16
private const val THIRTY_TWO = 32

fun String.toMd5(): String {
    val md = MessageDigest.getInstance(MD5)
    return BigInteger(ONE, md.digest(toByteArray())).toString(SIXTEEN).padStart(THIRTY_TWO, ZERO)
}
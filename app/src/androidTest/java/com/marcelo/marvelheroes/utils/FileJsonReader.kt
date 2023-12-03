package com.marcelo.marvelheroes.utils

import androidx.test.platform.app.InstrumentationRegistry
import com.marcelo.marvelheroes.extensions.emptyString
import org.koin.core.component.KoinComponent
import java.io.InputStreamReader

class FileJsonReader : KoinComponent {

    private val context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    }

    fun readJsonFile(fileName: String): String {
        return context.assets.open(fileName).use { inputStream ->
            InputStreamReader(inputStream, UTF_8).use { reader ->
                reader.readLines().joinToString(emptyString())
            }
        }
    }

    private companion object {
        const val UTF_8 = "UTF-8"
    }
}
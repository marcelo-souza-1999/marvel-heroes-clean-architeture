package com.marcelo.marvelheroes.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marcelo.marvelheroes.domain.datasource.LocalStorageDataSource
import com.marcelo.marvelheroes.extensions.emptyString
import com.marcelo.marvelheroes.utils.constants.MARVEL_LOCAL_STORE_NAME
import com.marcelo.marvelheroes.utils.constants.SORT_ORDER_BY_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocalStorageDataSourceImpl(
    private val context: Context
) : LocalStorageDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = MARVEL_LOCAL_STORE_NAME
    )

    private val sortingKey = stringPreferencesKey(SORT_ORDER_BY_KEY)

    override val sort: Flow<String>
        get() = context.dataStore.data.map { marvelStore ->
            marvelStore[sortingKey] ?: emptyString()
        }

    override suspend fun saveSort(sorting: String) {
        context.dataStore.edit { marvelStore ->
            marvelStore[sortingKey] = sorting
        }
    }
}

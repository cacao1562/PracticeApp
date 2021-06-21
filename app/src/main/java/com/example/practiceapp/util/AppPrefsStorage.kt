package com.example.practiceapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class AppPrefsStorage @Inject constructor(
    @ApplicationContext private val context: Context) {

    private val IS_DARK_THEME = booleanPreferencesKey("IS_DARK_THEME")

    val isDarkTheme: Flow<Boolean>
        get() = context.dataStore.getValueAsFlow(IS_DARK_THEME, false)

    suspend fun setDarkTheme(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = value
        }
    }
    suspend fun clearPreferenceStorage() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun <T> DataStore<Preferences>.getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                // we try again to store the value in the map operator
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // return the default value if it doesn't exist in the storage
            preferences[key] ?: defaultValue
        }
    }
}


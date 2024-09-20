package com.example.core.data.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.model.data.ItemModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore by preferencesDataStore(name = "app_preferences")

interface DataStoreManager {
    suspend fun saveCurrentUserId(userId: String)
    fun getUserId(): Flow<String?>
    suspend fun clearUserId()

    suspend fun saveItemModel(model: ItemModel)
    fun getItemModel(): Flow<ItemModel?>
    suspend fun clearItemModel()

    suspend fun clearAllData()
}

class DataStoreManagerImpl @Inject constructor(@ApplicationContext private val context: Context) :
    DataStoreManager {

    private val gson = Gson()

    companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val ITEM_MODEL_KEY = stringPreferencesKey("item_model")
    }


    override suspend fun saveCurrentUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    override fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    override suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }

    override suspend fun saveItemModel(model: ItemModel) {
        val modelJson = gson.toJson(model)
        context.dataStore.edit { preferences ->
            preferences[ITEM_MODEL_KEY] = modelJson
        }
    }

    override fun getItemModel(): Flow<ItemModel?> {
        return context.dataStore.data.map { preferences ->
            val modelJson = preferences[ITEM_MODEL_KEY]
            if (modelJson != null) {
                gson.fromJson(modelJson, ItemModel::class.java)
            } else {
                null
            }
        }
    }

    override suspend fun clearItemModel() {
        context.dataStore.edit { preferences ->
            preferences.remove(ITEM_MODEL_KEY)
        }
    }

    override suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
package com.example.core.data.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core.model.data.ItemModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

interface DataStoreManager {
    suspend fun saveCurrentUserId(userId: String)
    fun getUserId(): Flow<String>
    suspend fun clearUserId()
    suspend fun saveCurrentName(name: String)
    fun getName(): Flow<String>
    suspend fun clearName()

    suspend fun saveItemModel(model: ItemModel)
    fun getItemModel(): Flow<ItemModel?>
    suspend fun clearItemModel()

    suspend fun clearAllData()
}

class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreManager {

    private val gson = Gson()

    companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val ITEM_MODEL_KEY = stringPreferencesKey("item_model")
    }


    override suspend fun saveCurrentUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    override fun getUserId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: "No User ID"
        }
    }

    override suspend fun clearUserId() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }

    override suspend fun saveCurrentName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    override fun getName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    }

    override suspend fun clearName() {
        dataStore.edit { preferences ->
            preferences.remove(USER_NAME_KEY)
        }
    }

    override suspend fun saveItemModel(model: ItemModel) {
        val modelJson = gson.toJson(model)
        dataStore.edit { preferences ->
            preferences[ITEM_MODEL_KEY] = modelJson
        }
    }

    override fun getItemModel(): Flow<ItemModel?> {
        return dataStore.data.map { preferences ->
            val modelJson = preferences[ITEM_MODEL_KEY]
            Timber.d("itemModel:1 $modelJson")
            if (modelJson != null) {
                val item = gson.fromJson(modelJson, ItemModel::class.java)
                Timber.d("itemModel:2 ${item}")
                item
            } else {
                null
            }
        }
    }

    override suspend fun clearItemModel() {
        dataStore.edit { preferences ->
            preferences.remove(ITEM_MODEL_KEY)
        }
    }

    override suspend fun clearAllData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
package hu.ahomolya.androidbase.store.impl

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import hu.ahomolya.androidbase.adapter.InstantAdapter
import hu.ahomolya.androidbase.model.SavedTokens
import hu.ahomolya.androidbase.store.PreferencesStore

class PreferencesStoreImpl(private val sharedPreferences: SharedPreferences) : PreferencesStore {
    private val moshi by lazy { Moshi.Builder().add(InstantAdapter).build() }

    override var tokens: SavedTokens?
        get() = sharedPreferences.getString(KEY_SAVED_TOKENS, null)?.let { moshi.adapter(SavedTokens::class.java).fromJson(it) }
        set(value) {
            sharedPreferences.edit().putString(KEY_SAVED_TOKENS, value?.let { moshi.adapter(SavedTokens::class.java).toJson(it) }).apply()
        }

    companion object {
        private const val KEY_SAVED_TOKENS = "KEY_SAVED_TOKENS"
    }
}
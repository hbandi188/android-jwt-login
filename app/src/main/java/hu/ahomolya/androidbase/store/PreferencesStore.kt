package hu.ahomolya.androidbase.store

import hu.ahomolya.androidbase.model.SavedTokens

interface PreferencesStore {
    /**
     * Authentication tokens for the user.
     */
    var tokens: SavedTokens?
}
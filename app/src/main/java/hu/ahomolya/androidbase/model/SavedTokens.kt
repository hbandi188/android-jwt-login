package hu.ahomolya.androidbase.model

import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class SavedTokens(
    val accessToken: String,
    val refreshToken: String,
    val validUntil: Instant,
)

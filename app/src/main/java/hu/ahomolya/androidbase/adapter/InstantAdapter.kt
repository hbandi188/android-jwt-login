package hu.ahomolya.androidbase.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant

object InstantAdapter {
    @ToJson
    fun toJson(instant: Instant?): Long? = instant?.toEpochMilli()

    @FromJson
    fun fromJson(timestamp: Long?): Instant? = timestamp?.let { Instant.ofEpochMilli(it) }
}
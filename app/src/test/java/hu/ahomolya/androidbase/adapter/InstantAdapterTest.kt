package hu.ahomolya.androidbase.adapter

import hu.ahomolya.androidbase.BaseUnitTest
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.Instant

class InstantAdapterTest : BaseUnitTest() {
    @Test
    fun `should serialize instant to long`() {
        val timestamp = 1630271828000

        InstantAdapter.toJson(Instant.ofEpochMilli(timestamp)) shouldBe timestamp

        InstantAdapter.fromJson(timestamp) shouldBe Instant.ofEpochMilli(timestamp)
    }

    @Test
    fun `should handle null values`() {
        InstantAdapter.toJson(null).shouldBeNull()
        InstantAdapter.fromJson(null).shouldBeNull()
    }
}
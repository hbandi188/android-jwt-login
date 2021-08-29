package hu.ahomolya.androidbase.store.impl

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.adapter.InstantAdapter
import hu.ahomolya.androidbase.model.SavedTokens
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant

class PreferencesStoreImplTest : BaseUnitTest() {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var tested: PreferencesStoreImpl

    private val moshi by lazy { Moshi.Builder().add(InstantAdapter).build() }

    @Before
    fun setup() {
        editor = mockk {
            every { putString(any(), any()) } returns this
            justRun { apply() }
        }
        prefs = mockk {
            every { edit() } returns editor
            every { getString(any(), any()) } returns null
        }

        tested = PreferencesStoreImpl(prefs)
    }

    @Test
    fun `saves tokens to preferences store`() {
        tested.tokens = SavedTokens("", "", Instant.now())

        val jsonSlot = slot<String>()
        verify { editor.putString(any(), capture(jsonSlot)) }

        val savedObject = moshi.adapter(SavedTokens::class.java).fromJson(jsonSlot.captured)
        savedObject.shouldNotBeNull()
    }

    @Test
    fun `can clear saved tokens`() {
        tested.tokens = null

        verify { editor.putString(any(), null) }
    }

    @Test
    fun `should not fail if tokens are missing`() {
        val result = tested.tokens

        result.shouldBeNull()
        verify { prefs.getString(any(), null) }
    }

    @Test
    fun `should read tokens as json`() {
        val savedTokens = SavedTokens("", "", Instant.ofEpochMilli(1_000_000))
        val json = moshi.adapter(SavedTokens::class.java).toJson(savedTokens)
        every { prefs.getString(any(), null) } returns json

        val result = tested.tokens

        result.shouldNotBeNull()
        result shouldBe savedTokens
        verify { prefs.getString(any(), null) }
    }
}
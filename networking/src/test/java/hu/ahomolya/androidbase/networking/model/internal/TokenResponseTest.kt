package hu.ahomolya.androidbase.networking.model.internal

import com.squareup.moshi.Moshi
import hu.ahomolya.androidbase.networking.BaseUnitTest
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.Test

class TokenResponseTest : BaseUnitTest() {
    private val moshi by lazy { Moshi.Builder().build() }

    @Test
    fun `should be serializable with Moshi`() {
        val json = loadFile("login_success.json")

        val parsed = moshi.adapter(TokenResponse::class.java).fromJson(json)

        parsed.shouldNotBeNull()
    }
}
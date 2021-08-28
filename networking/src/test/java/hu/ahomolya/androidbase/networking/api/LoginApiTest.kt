package hu.ahomolya.androidbase.networking.api

import hu.ahomolya.androidbase.networking.BaseUnitTest
import io.kotest.matchers.shouldBe
import org.junit.Test
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class LoginApiTest : BaseUnitTest() {
    @Test
    fun `correct annotations on login method`() {
        LoginApi::login.annotations.any { it is POST } shouldBe true
        LoginApi::login.annotations.any { it is FormUrlEncoded } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "username" } } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "password" } } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "grant_type" } } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "client_id" } } shouldBe true
    }
}
package hu.ahomolya.androidbase.networking.api

import hu.ahomolya.androidbase.networking.BaseUnitTest
import hu.ahomolya.androidbase.networking.model.internal.TokenResponse
import io.kotest.matchers.shouldBe
import org.junit.Test
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import kotlin.reflect.jvm.jvmErasure

class LoginApiTest : BaseUnitTest() {
    @Test
    fun `correct annotations on login method`() {
        LoginApi::login.annotations.any { it is POST && it.value == "/idp/api/v1/token" } shouldBe true
        LoginApi::login.annotations.any { it is FormUrlEncoded } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "username" } } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "password" } } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "grant_type" } } shouldBe true
        LoginApi::login.parameters.any { param -> param.annotations.any { it is Field && it.value == "client_id" } } shouldBe true
        LoginApi::login.isSuspend shouldBe true
    }

    @Test
    fun `login() should return a token response`() {
        LoginApi::login.returnType.jvmErasure shouldBe TokenResponse::class
    }

    @Test
    fun `correct annotations on refresh method`() {
        LoginApi::refresh.annotations.any { it is POST && it.value == "/idp/api/v1/token" } shouldBe true
        LoginApi::refresh.annotations.any { it is FormUrlEncoded } shouldBe true
        LoginApi::refresh.parameters.any { param -> param.annotations.any { it is Field && it.value == "refresh_token" } } shouldBe true
        LoginApi::refresh.parameters.any { param -> param.annotations.any { it is Field && it.value == "grant_type" } } shouldBe true
        LoginApi::refresh.parameters.any { param -> param.annotations.any { it is Field && it.value == "client_id" } } shouldBe true
        LoginApi::refresh.isSuspend shouldBe true
    }

    @Test
    fun `refresh() should return a token response`() {
        LoginApi::refresh.returnType.jvmErasure shouldBe TokenResponse::class
    }
}
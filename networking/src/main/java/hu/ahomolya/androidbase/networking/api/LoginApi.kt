package hu.ahomolya.androidbase.networking.api

import hu.ahomolya.androidbase.networking.model.internal.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface LoginApi {
    @POST("/idp/api/v1/token")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
    ): TokenResponse

    @POST("/idp/api/v1/token")
    @FormUrlEncoded
    suspend fun refresh(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
    ): TokenResponse
}

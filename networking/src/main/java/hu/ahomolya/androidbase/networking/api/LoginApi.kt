package hu.ahomolya.androidbase.networking.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface LoginApi {
    @POST
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
    )
}

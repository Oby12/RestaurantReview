package com.learn.restaurantreview.data.retrofit

import com.learn.restaurantreview.data.response.PostReviewResponse
import retrofit2.Call
import com.learn.restaurantreview.data.response.RestaurantResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("detail/{id}")
    fun getRestaurantOby(
        @Path("id") id: String

    ): Call<RestaurantResponse>

    //di bawha ini untuk menambhkn data
    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        //Kemudian, Anda harus memakai anotasi @FormUrlEncoded untuk mengirimkan data dengan menggunakan @Field
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("review") review : String
        //Pastikan key yang dimasukkan pada @Field harus sama dengan field yang ada pada API. Jika tidak sama, data tidak akan terkirim
    ) : Call<PostReviewResponse>
}
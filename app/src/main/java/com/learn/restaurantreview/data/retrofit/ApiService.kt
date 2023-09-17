package com.learn.restaurantreview.data.retrofit

import retrofit2.Call
import com.learn.restaurantreview.data.response.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("detail/{id}")
    fun getRestaurantOby(
        @Path("id") id: String

    ): Call<RestaurantResponse>
}
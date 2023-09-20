package com.learn.restaurantreview.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.restaurantreview.data.response.CustomerReviewsItem
import com.learn.restaurantreview.data.response.PostReviewResponse
import com.learn.restaurantreview.data.response.Restaurant
import com.learn.restaurantreview.data.response.RestaurantResponse
import com.learn.restaurantreview.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    //Lalu buat beberapa variabel untuk menampung data restoran, review, dan status loading
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    //documemntation
    /*Inilah yang disebut dengan encapsulation pada LiveData, yaitu dengan membuat data yang bertipe MutableLiveData menjadi private (_listReview)
    dan yang bertipe LiveData menjadi public (listReview). Cara ini disebut dengan backing property. Dengan begitu Anda dapat mencegah variabel yang
    bertipe MutableLiveData diubah dari luar class. Karena memang seharusnya hanya ViewModel-lah yang dapat mengubah data.*/
    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    //namun bedanya MutableLiveData bisa kita ubah value-nya
    private val _isLoading = MutableLiveData<Boolean>()
    //sedangkan LiveData bersifat read-only (tidak dapat diubah).
    val isLoading: LiveData<Boolean> = _isLoading

    //variabel untuk menyimpan text yang akan ditampilkan Snackbar
    private val _snackbartext = MutableLiveData<String>()
    val snackbarText : LiveData<String> = _snackbartext

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        //Yang dimaksud mengubah value-nya adalah pada bagian ini
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurantOby(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReview.value = response.body()?.restaurant?.customerReviews
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postReview(review: String) {
        _isLoading.value = true
        //Intinya adalah setValue() bekerja di main thread dan postValue() bekerja di background thread.
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(call: Call<PostReviewResponse>, response: Response<PostReviewResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()?.customerReviews
                    _snackbartext.value = response.body()?.message
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
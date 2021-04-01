
package com.nanang.berberita.data.api


import com.udindev.ngaos.utils.GlobalConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = GlobalConfig.url_time_prayer

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}
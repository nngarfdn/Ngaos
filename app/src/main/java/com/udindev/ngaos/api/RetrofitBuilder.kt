
package com.udindev.ngaos.api


import com.udindev.ngaos.utils.GlobalConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = GlobalConfig.url_time_prayer

    private const val BASE_URL_KELAS = "https://ngaos-api-interface.herokuapp.com/api/v1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build() //Doesn't require the adapter
    }

    private fun getRetrofitKelas(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_KELAS)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    val apiServiceKelas : ApiService = getRetrofitKelas().create(ApiService::class.java)

    var apiServiceDiikuti : ApiService = getRetrofitKelas().create(ApiService::class.java)

}
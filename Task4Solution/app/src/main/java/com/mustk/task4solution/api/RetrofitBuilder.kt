package com.mustk.task4solution.api

import com.mustk.task4solution.util.Constant.APIKEY_QUERY_PARAM
import com.mustk.task4solution.util.Constant.API_KEY
import com.mustk.task4solution.util.Constant.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    object TokenInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var original = chain.request()
            val url = original.url().newBuilder()
                .addQueryParameter(APIKEY_QUERY_PARAM, API_KEY).build()
            original = original.newBuilder()
                .url(url)
                .build()
            return chain.proceed(original)
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(TokenInterceptor)
        .build()

    private fun getRetrofit () : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val movieService : ApiService =
        getRetrofit().create(ApiService::class.java)
}
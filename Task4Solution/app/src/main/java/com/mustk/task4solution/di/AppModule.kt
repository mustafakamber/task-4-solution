package com.mustk.task4solution.di

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.request.RequestOptions
import com.mustk.task4solution.R
import com.mustk.task4solution.data.datasource.MovieDataSource
import com.mustk.task4solution.data.service.MovieService
import com.mustk.task4solution.domain.MovieRepository
import com.mustk.task4solution.shared.Constant.APIKEY_QUERY_PARAM
import com.mustk.task4solution.shared.Constant.API_KEY
import com.mustk.task4solution.shared.Constant.BASE_URL
import com.mustk.task4solution.util.placeHolderProgressBar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTokenInterceptor(): Interceptor {
        return Interceptor { chain ->
            var original = chain.request()
            val url = original.url().newBuilder()
                .addQueryParameter(APIKEY_QUERY_PARAM, API_KEY).build()
            original = original.newBuilder()
                .url(url)
                .build()
            chain.proceed(original)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    fun provideMovieDataSource(movieService : MovieService): MovieDataSource {
        return MovieRepository(movieService)
    }
}
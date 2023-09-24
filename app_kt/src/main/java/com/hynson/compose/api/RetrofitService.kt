package com.hynson.compose.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {


    /**
     * okhttp client
     */
    lateinit var okHttpClient: OkHttpClient

    /**
     * 主Url地址
     */
    private const val BASEAPI = "https://www.wanandroid.com/";

    /**
     * 创建service对象
     */
    fun <T> createService(mClass: Class<T>): T {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASEAPI)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(mClass) as T
    }

}
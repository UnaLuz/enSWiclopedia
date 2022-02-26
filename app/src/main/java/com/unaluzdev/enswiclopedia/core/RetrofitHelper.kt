package com.unaluzdev.enswiclopedia.core

import com.unaluzdev.enswiclopedia.util.BASE_INFO_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_INFO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
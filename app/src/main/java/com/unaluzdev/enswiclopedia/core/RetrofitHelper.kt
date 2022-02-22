package com.unaluzdev.enswiclopedia.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://swapi.py4e.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
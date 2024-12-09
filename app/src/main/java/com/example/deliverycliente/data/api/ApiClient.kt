package com.example.deliverycliente.data.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://proyectodelivery.jmacboy.com/api/"

    private lateinit var context: Context

    // Configuración del interceptor para logs
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Interceptor para el token de autenticación
    private val authInterceptor = Interceptor { chain ->
        val token = context.getSharedPreferences("delivery_prefs", Context.MODE_PRIVATE)
            .getString("auth_token", null)

        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        chain.proceed(request.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    fun getApiService(): ApiService {
        return createService(ApiService::class.java)
    }

    fun init(context: Context) {
        this.context = context
    }
}

package com.yaman.network_utils

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getAPIClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .client(okHttpClient)
                .build()
        }
        return retrofit
    }

    private val okHttpClient: OkHttpClient
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request: Request.Builder = chain.request().newBuilder()
                    val headers: HashMap<String, String> = RetrofitServiceGenerator.getHeaders()

                    for (key in headers.keys) {
                        val value = headers[key]
                        value?.let { request.addHeader(key, it) }
                    }

                    request.method(chain.request().method, chain.request().body)
                    request.build()
                    chain.proceed(request.build())
                })
                .readTimeout(100, TimeUnit.SECONDS)
                .build()
        }


}


/** Generate New Retrofit Service **/
object RetrofitServiceGenerator {

    fun <S> createService(serviceClass: Class<S>,baseUrl: String): S {
        return RetrofitClient.getAPIClient(baseUrl)!!.create(serviceClass)
    }

    private val headers = HashMap<String, String>()

    fun addHeader(key: String, value: String) {
        headers[key] = value
    }

    fun getHeaders(): HashMap<String, String> {
        return headers
    }

}
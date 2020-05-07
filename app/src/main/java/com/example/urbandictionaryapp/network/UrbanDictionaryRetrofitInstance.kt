package com.example.urbandictionaryapp.network


import com.example.urbandictionaryapp.model.DefinitionResults
import com.example.urbandictionaryapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UrbanDictionaryRetrofitInstance {
    private val dictionaryService: DictionaryService
    private val client: OkHttpClient


    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createUDService(retrofitInstance: Retrofit): DictionaryService {
        return retrofitInstance.create(DictionaryService::class.java)
    }

    fun getDefinitions(term: String): Call<DefinitionResults> {
        return dictionaryService.getDefinition(term)
    }


    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        dictionaryService = createUDService(getRetrofitInstance())
    }
}
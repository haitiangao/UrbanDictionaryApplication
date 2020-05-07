package com.example.urbandictionaryapp.repository

import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.model.DefinitionResults
import com.example.urbandictionaryapp.network.UrbanDictionaryRetrofitInstance
import com.example.urbandictionaryapp.util.DebugLogger
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrbanDictionaryRepository private constructor() {

    private val urbanDictionaryRetrofitInstance: UrbanDictionaryRetrofitInstance =
        UrbanDictionaryRetrofitInstance()

    private val publishSubject: PublishSubject<List<Definition>> = PublishSubject.create()


    companion object {
        @Volatile
        private var INSTANCE: UrbanDictionaryRepository? = null

        fun getRepository(): UrbanDictionaryRepository {
            return INSTANCE ?: synchronized(this) {
                UrbanDictionaryRepository().also {
                    INSTANCE = it
                }
            }
        }
    }

    fun getDefinitionList(query: String): Observable<List<Definition>> {
        urbanDictionaryRetrofitInstance.getDefinitions(query)
            .enqueue(object : Callback<DefinitionResults> {
                override fun onResponse(
                    call: Call<DefinitionResults>,
                    response: Response<DefinitionResults>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let { definitionResults ->
                            publishSubject.onNext(definitionResults.list)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<DefinitionResults>,
                    t: Throwable
                ) {
                    DebugLogger.logError(t)
                }
            })

        return publishSubject
    }


}
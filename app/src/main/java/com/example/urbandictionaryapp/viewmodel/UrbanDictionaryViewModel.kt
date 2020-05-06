package com.example.urbandictionaryapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.network.UrbanDictionaryRetrofitInstance
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UrbanDictionaryViewModel(application: Application) : AndroidViewModel(application) {

    private val urbanDictionaryRetrofitInstance: UrbanDictionaryRetrofitInstance =
        UrbanDictionaryRetrofitInstance()

    fun getDefinitionListRx(query: String): Observable<List<Definition>> {
        return urbanDictionaryRetrofitInstance.getDefinitions(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.list
            }
    }


}
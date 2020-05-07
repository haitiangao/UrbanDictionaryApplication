package com.example.urbandictionaryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.repository.UrbanDictionaryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UrbanDictionaryViewModel : ViewModel() {

    private val definitionData: MutableLiveData<List<Definition>> = MutableLiveData()
    private val repository = UrbanDictionaryRepository.getRepository()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun searchForDefinitions(definitionSearch: String) {
        compositeDisposable.add(repository.getDefinitionList(definitionSearch)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { definitions ->
                setRetrievedDefinitions(definitions)
            })
    }

    private fun setRetrievedDefinitions(definitions: List<Definition>?) {
        definitionData.value = definitions
        compositeDisposable.clear()//Clear each time because each search is adding a new disposable
    }

    fun getDefinitionLiveData(): LiveData<List<Definition>> = definitionData

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}
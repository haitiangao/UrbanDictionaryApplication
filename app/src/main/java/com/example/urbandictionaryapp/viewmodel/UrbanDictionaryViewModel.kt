package com.example.urbandictionaryapp.viewmodel

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.urbandictionaryapp.adapter.DefinitionAdapter
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.model.DefinitionResults
import com.example.urbandictionaryapp.network.UrbanDictionaryRetrofitInstance
import com.example.urbandictionaryapp.repository.UrbanDictionaryRepository
import com.example.urbandictionaryapp.util.DebugLogger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class UrbanDictionaryViewModel(application: Application) : AndroidViewModel(application) {

    private val urbanDictionaryRetrofitInstance: UrbanDictionaryRetrofitInstance =
        UrbanDictionaryRetrofitInstance()
    private val repository = UrbanDictionaryRepository.getRepository()


    fun getEntries(): MutableList<Definition> {
        return repository.getEntries()
    }
    fun setEntries(entries: MutableList<Definition>){
        repository.setEntries(entries)
    }




    fun getDefinitionList(query: String): MutableLiveData<MutableList<Definition>> {
        urbanDictionaryRetrofitInstance.getDefinitions(query)
            .enqueue(object:Callback<DefinitionResults>{
                override fun onResponse(
                    call :Call<DefinitionResults>,
                    response: Response<DefinitionResults>
                ){
                    if (response.isSuccessful&&response.body()!=null) {
                        val tempMutableLiveData = repository.getDefinitionLiveData()
                        tempMutableLiveData.value=response.body()!!.list
                        repository.setDefinitionLiveData(tempMutableLiveData)
                    }
                }
                override fun onFailure(
                     call: Call<DefinitionResults>,
                     t: Throwable
                ){
                    DebugLogger.logError(t)
                }
            })

        return repository.getDefinitionLiveData()
    }


}
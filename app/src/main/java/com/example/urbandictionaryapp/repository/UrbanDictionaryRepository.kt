package com.example.urbandictionaryapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.urbandictionaryapp.model.Definition
import java.util.ArrayList

class UrbanDictionaryRepository private constructor(){

    private var definitionLiveData: MutableLiveData<MutableList<Definition>> = MutableLiveData()
    val dLiveData: LiveData<MutableList<Definition>> = definitionLiveData
    private var entries: MutableList<Definition> = ArrayList()


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

    fun getDefinitionLiveData(): MutableLiveData<MutableList<Definition>> {
        return definitionLiveData
    }
    fun setDefinitionLiveData(definitionLiveData: MutableLiveData<MutableList<Definition>>){
        this.definitionLiveData = definitionLiveData
    }
    fun getEntries(): MutableList<Definition> {
        return entries
    }
    fun setEntries(entries: MutableList<Definition>){
        this.entries = entries
    }


}
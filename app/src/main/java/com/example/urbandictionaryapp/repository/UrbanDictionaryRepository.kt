package com.example.urbandictionaryapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.urbandictionaryapp.model.Definition
import java.util.ArrayList

class UrbanDictionaryRepository{

    private var definitionLiveData: MutableLiveData<MutableList<Definition>> = MutableLiveData()
    private var entries: MutableList<Definition> = ArrayList()


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
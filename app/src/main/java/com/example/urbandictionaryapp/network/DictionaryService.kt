package com.example.urbandictionaryapp.network


import com.example.urbandictionaryapp.model.DefinitionResults
import com.example.urbandictionaryapp.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DictionaryService {

    //"https://mashape-community-urban-dictionary.p.rapidapi.com/define?term=?”
    @Headers(
        "x-rapidapi-host:mashape-community-urban-dictionary.p.rapidapi.com",
        "x-rapidapi-key:aec6785688msh1383368425074c5p12267bjsnfa2f9900be26"
    )
    @GET(Constants.GET_URL_POSTFIX)
    fun getDefinition(@Query("term") term: String): Call<DefinitionResults>

}
package com.example.urbandictionaryapp.viewmodel

import android.view.View
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.repository.UrbanDictionaryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class UrbanDictionaryViewModelTest {

    private lateinit var testViewModel: UrbanDictionaryViewModel
    private lateinit var testRepository: UrbanDictionaryRepository

    @Mock
    private lateinit var mockDefinition: Definition

    @Before
    fun setUp() {
        testViewModel = UrbanDictionaryViewModel(ApplicationProvider.getApplicationContext())
        testRepository = UrbanDictionaryRepository.getRepository()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getSetEntriesCorrectness(){
        `when`(mockDefinition.word).thenReturn("wat")
        val definitionList = mutableListOf(mockDefinition)
        testViewModel.setEntries(definitionList)
        val checkList = testViewModel.getEntries()

        assert(checkList[0].word=="wat")
    }



}
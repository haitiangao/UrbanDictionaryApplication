package com.example.urbandictionaryapp.viewmodel

import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.urbandictionaryapp.model.Definition
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class UrbanDictionaryViewModelTest {

    private lateinit var testViewModel: UrbanDictionaryViewModel

    @Mock
    private lateinit var mockDefinition: Definition

    @Before
    fun setUp() {
        testViewModel = UrbanDictionaryViewModel(ApplicationProvider.getApplicationContext())
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

    @Test
    fun checkGetDefinitionListContainsItems(){
        var testList :MutableList<Definition>
        val testObserver:Observer<MutableList<Definition>> = Observer{
            testList=it
            assert(testList.size>0)
        }

        testViewModel.getDefinitionList("wat").observeForever(testObserver)

    }


}
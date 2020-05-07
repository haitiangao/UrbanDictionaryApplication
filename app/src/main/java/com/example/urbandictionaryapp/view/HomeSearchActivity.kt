package com.example.urbandictionaryapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.adapter.DefinitionAdapter
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.util.Constants.Companion.DEFINITION_KEY
import com.example.urbandictionaryapp.util.SortingUtil.Companion.sortByThumbsDown
import com.example.urbandictionaryapp.util.SortingUtil.Companion.sortByThumbsUp
import com.example.urbandictionaryapp.viewmodel.UrbanDictionaryViewModel
import kotlinx.android.synthetic.main.home_search_activity_layout.*

class HomeSearchActivity : AppCompatActivity(), DefinitionAdapter.UserClickListener {

    private lateinit var viewModel: UrbanDictionaryViewModel
    private lateinit var definitionAdapter: DefinitionAdapter
    private lateinit var listObserver: Observer<List<Definition>>
    private var currentDefinitions: List<Definition> = mutableListOf()

    private val searchTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
        }

        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            doSearch(charSequence.toString().trim())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_search_activity_layout)

        viewModel =
            ViewModelProvider(this).get<UrbanDictionaryViewModel>(UrbanDictionaryViewModel::class.java)

        setUpRecyclerView()

        (word_editText as EditText).addTextChangedListener(searchTextWatcher)

        listObserver =
            Observer {
                setEntries(it)
                progressBar1.visibility = View.GONE
            }
        viewModel.getDefinitionLiveData().observe(this, listObserver)

        mostThumbsUpButton.setOnClickListener {
            if (currentDefinitions.isNotEmpty()) {
                setEntries(sortByThumbsUp(currentDefinitions))
            }
        }

        mostThumbsDownButton.setOnClickListener {
            if (currentDefinitions.isNotEmpty()) {
                setEntries(sortByThumbsDown(currentDefinitions))
            }
        }
    }

    private fun setUpRecyclerView() {
        val itemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)

        definitionAdapter = DefinitionAdapter(mutableListOf(), this)
        definition_recyclerView.adapter = definitionAdapter

        definition_recyclerView.layoutManager = LinearLayoutManager(this)
        definition_recyclerView.addItemDecoration(itemDecoration)
    }

    private fun setEntries(foundDefinitions: List<Definition>) {
        currentDefinitions = foundDefinitions
        (definition_recyclerView.adapter as DefinitionAdapter).setEntries(foundDefinitions)
        (definition_recyclerView.adapter as DefinitionAdapter).notifyDataSetChanged()
    }


    fun doSearch(query: String) {
        progressBar1.visibility = View.VISIBLE
        viewModel.searchForDefinitions(query)
    }

    override fun openDefinition(definition: Definition) {
        val openMovieIntent = Intent(this, DisplayDefinitionActivity::class.java)
        openMovieIntent.putExtra(DEFINITION_KEY, definition)
        startActivity(openMovieIntent)
    }
}

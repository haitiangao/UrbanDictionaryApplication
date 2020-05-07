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
import com.example.urbandictionaryapp.repository.UrbanDictionaryRepository
import com.example.urbandictionaryapp.util.Constants
import com.example.urbandictionaryapp.util.DebugLogger
import com.example.urbandictionaryapp.viewmodel.UrbanDictionaryViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_search_activity_layout.*
import java.util.*


class HomeSearchActivity : AppCompatActivity(),DefinitionAdapter.UserClickListener {
    private lateinit var viewModel: UrbanDictionaryViewModel
    private lateinit var definitionAdapter: DefinitionAdapter
    private lateinit var listObserver: Observer<MutableList<Definition>>

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


        definitionAdapter = DefinitionAdapter(viewModel.getEntries(),this)
        definition_recyclerView.adapter = definitionAdapter

        setUpRecyclerView()

        (word_editText as EditText).addTextChangedListener(searchTextWatcher)


        mostThumbsUpButton.setOnClickListener {
            if (viewModel.getEntries().size > 0) {
                val sortedEntries = viewModel.getEntries().sortedWith(compareByDescending { it.thumbsUp })
                setEntries(sortedEntries)
            }
        }

        mostThumbsDownButton.setOnClickListener {
            if (viewModel.getEntries().size > 0) {
                val sortedEntries = viewModel.getEntries().sortedWith(compareByDescending { it.thumbsDown })
                setEntries(sortedEntries)
            }
        }


    }

    private fun setUpRecyclerView() {
        val itemDecoration =
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        definition_recyclerView.layoutManager = LinearLayoutManager(this)
        definition_recyclerView.adapter = DefinitionAdapter(viewModel.getEntries(), this)
        definition_recyclerView.addItemDecoration(itemDecoration)
    }

    private fun setEntries(updatedEntries: List<Definition>) {
        viewModel.setEntries(updatedEntries as MutableList<Definition>)
        (definition_recyclerView.adapter as DefinitionAdapter).setEntries(updatedEntries)
        (definition_recyclerView.adapter as DefinitionAdapter).notifyDataSetChanged()
    }


    fun doSearch(query: String) {
        progressBar1.visibility = View.VISIBLE

        listObserver =
            Observer {
                setEntries(it)
                progressBar1.visibility = View.GONE
            }

        viewModel.getDefinitionList(query).observe(this,listObserver )
    }

    override fun openDefinition(definition: Definition) {
        val openMovieIntent = Intent(this, DisplayDefinitionActivity::class.java)
        openMovieIntent.putExtra(Constants.DEFINITION_KEY, definition)
        startActivity(openMovieIntent)
    }
}

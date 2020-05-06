package com.example.urbandictionaryapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.adapter.DefinitionAdapter
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.util.Constants
import com.example.urbandictionaryapp.util.DebugLogger
import com.example.urbandictionaryapp.viewmodel.UrbanDictionaryViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_search_activity_layout.*
import java.util.*


class HomeSearchActivity : AppCompatActivity(),DefinitionAdapter.UserClickListener {
    private lateinit var viewModel: UrbanDictionaryViewModel
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var entries: MutableList<Definition> = ArrayList<Definition>()
    private lateinit var definitionAdapter: DefinitionAdapter


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

        definitionAdapter = DefinitionAdapter(entries,this)
        definition_recyclerView.adapter = definitionAdapter

        viewModel =
            ViewModelProvider(this).get<UrbanDictionaryViewModel>(UrbanDictionaryViewModel::class.java)

        setUpRecyclerView()

        (word_editText as EditText).addTextChangedListener(searchTextWatcher)


        mostThumbsUpButton.setOnClickListener {
            if (entries.size > 0) {
                val sortedEntries = entries.sortedWith(compareByDescending { it.thumbsUp })
                setEntries(sortedEntries)
            }
        }

        mostThumbsDownButton.setOnClickListener {
            if (entries.size > 0) {
                val sortedEntries = entries.sortedWith(compareByDescending { it.thumbsDown })
                setEntries(sortedEntries)
            }
        }


    }

    private fun setUpRecyclerView() {
        val itemDecoration =
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        definition_recyclerView.layoutManager = LinearLayoutManager(this)
        definition_recyclerView.adapter = DefinitionAdapter(entries, this)
        definition_recyclerView.addItemDecoration(itemDecoration)
    }

    private fun setEntries(updatedEntries: List<Definition>) {
        entries = updatedEntries as MutableList<Definition>
        (definition_recyclerView.adapter as DefinitionAdapter).setEntries(updatedEntries)
        (definition_recyclerView.adapter as DefinitionAdapter).notifyDataSetChanged()
    }


    private fun doSearch(query: String) {
        progressBar1.visibility = View.VISIBLE;
        compositeDisposable.add(viewModel.getDefinitionListRx(query).subscribe({ resultLibrary ->
            setEntries(resultLibrary)
            progressBar1.visibility = View.GONE
            clearDisposable()
        }, { throwable ->
            DebugLogger.logError(throwable)
            clearDisposable()
        }))
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun openDefinition(definition: Definition) {
        val openMovieIntent = Intent(this, DisplayDefinitionActivity::class.java)
        openMovieIntent.putExtra(Constants.DEFINITION_KEY, definition)
        startActivity(openMovieIntent)
    }
}

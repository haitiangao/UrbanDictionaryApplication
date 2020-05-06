package com.example.urbandictionaryapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.model.Definition
import com.example.urbandictionaryapp.util.Constants
import kotlinx.android.synthetic.main.display_definition_activity.*

class DisplayDefinitionActivity : AppCompatActivity() {

    private lateinit var definition: Definition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_definition_activity)

        val data = intent.extras!!
        definition = data.getParcelable(Constants.DEFINITION_KEY)!!

        definitionNameView.text = definition.word
        thumbsUpView2.text=getString(R.string.thumbs_up, definition.thumbsUp)
        thumbsDownView2.text=getString(R.string.thumbs_down, definition.thumbsDown)
        definitionTextView2.text=getString(R.string.definition, definition.definition)
        exampleTextView.text=getString(R.string.example, definition.example)
    }
}
package com.example.urbandictionaryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionaryapp.R
import com.example.urbandictionaryapp.databinding.DefinitionItemLayoutBinding
import com.example.urbandictionaryapp.model.Definition

class DefinitionAdapter(
    private var entries: List<Definition>,
    private var userClickListener: UserClickListener
) : RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder>() {

    interface UserClickListener {
        fun openDefinition(definition: Definition)
    }
    fun setEntries(updatedEntries: List<Definition>){
        entries = updatedEntries
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DefinitionItemLayoutBinding.inflate(inflater)

        return DefinitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int){
        holder.bind(entries[position])
        holder.itemView.setOnClickListener {
            userClickListener.openDefinition(entries[position])
        }

    }

    override fun getItemCount() = entries.size

    inner class DefinitionViewHolder(private val binding: DefinitionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Definition) {
            with(binding) {
                nameView.text = item.word
                thumbsUpView.text =
                    binding.root.context.getString(R.string.thumbs_up, item.thumbsUp)
                thumbsDownView.text =
                    binding.root.context.getString(R.string.thumbs_down, item.thumbsDown)
                definitionView.text =
                    binding.root.context.getString(R.string.definition, item.definition)
            }
        }
    }
}
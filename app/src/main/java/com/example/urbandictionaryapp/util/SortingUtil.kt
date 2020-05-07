package com.example.urbandictionaryapp.util

import com.example.urbandictionaryapp.model.Definition

open class SortingUtil {
    companion object {
        fun sortByThumbsUp(definitions: List<Definition>): List<Definition> {
            return definitions.sortedWith(compareByDescending { it.thumbsUp })
        }

        fun sortByThumbsDown(definitions: List<Definition>): List<Definition> {
            return definitions.sortedWith(compareByDescending { it.thumbsDown })
        }
    }
}
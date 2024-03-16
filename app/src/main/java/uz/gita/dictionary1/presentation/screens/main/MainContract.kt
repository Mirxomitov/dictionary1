package uz.gita.dictionary1.presentation.screens.main

import android.database.Cursor
import uz.gita.dictionary1.data.sources.entity.WordEntity

interface MainContract {
    interface View {
        fun showWords(cursor : Cursor)
    }
    interface Model {
        fun getAllWords(): Cursor
        fun getEnglishWordsByQuery(query: String): Cursor
        fun updateWord(id : Long, isFavourite: Long)
        fun getUzbekWordsByQuery(query: String): Cursor
    }

    interface Presenter {
        fun loadWords()
        fun loadWords(query: String , isEnToUz : Boolean)
        fun updateWord(id : Long, isFavourite: Long)
    }
}
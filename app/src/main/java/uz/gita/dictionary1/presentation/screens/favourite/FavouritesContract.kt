package uz.gita.dictionary1.presentation.screens.favourite

import android.database.Cursor

interface FavouritesContract {
    interface View {
        fun showWords(cursor : Cursor)
    }
    interface Model {
        fun removeFromFavourite(id: Long)
        fun getAllFavouriteWords(): Cursor
    }

    interface Presenter {
        fun loadWords()
        fun removeFromFavourite(id: Long)
    }
}
package uz.gita.dictionary1.domain

import android.database.Cursor
import uz.gita.dictionary1.data.sources.entity.WordEntity

interface AppRepository {
    fun getAllWords(): Cursor
    fun getEnglishWordsByQuery(query: String): Cursor
    fun updateWord(id : Long, isFavourite: Long)
    fun getWordById(id: Long) : WordEntity
    fun getUzbekWordsByQuery(query: String): Cursor
    fun getAllFavouriteWords(): Cursor
    fun removeFromFavourite(id: Long)
}
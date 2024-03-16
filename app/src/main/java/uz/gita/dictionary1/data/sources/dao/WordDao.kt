package uz.gita.dictionary1.data.sources.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import uz.gita.dictionary1.data.sources.entity.WordEntity

@Dao
interface WordDao {
    @Query("SELECT * FROM dictionary")
    fun getAllWords(): Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE :query || '%'")
    fun getEnglishWordsByQuery(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :query || '%' ORDER BY uzbek GLOB '[A-Za-z]*' DESC, Upper(uzbek)")
    fun getUzbekWordsByQuery(query: String): Cursor

    @Query("SELECT * FROM dictionary WHERE id = :id")
    fun getWordById(id: Long): WordEntity

    @Query("UPDATE dictionary SET is_favourite = :isFavourite WHERE id = :id")
    fun updateIsFavourite(id: Long, isFavourite: Long)

    @Query("SELECT * FROM dictionary WHERE is_favourite = 1")
    fun getAllFavouriteWords(): Cursor

    @Query("UPDATE dictionary SET is_favourite = 0 WHERE id = :id")
    fun removeFromFavourite(id: Long)
}
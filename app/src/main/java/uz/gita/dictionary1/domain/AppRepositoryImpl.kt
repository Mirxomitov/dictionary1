package uz.gita.dictionary1.domain

import android.database.Cursor
import uz.gita.dictionary1.data.sources.MyDatabase

class AppRepositoryImpl : AppRepository {

    companion object {
        private var instance: AppRepository? = null

        fun init() {
            if (instance == null)
                instance = AppRepositoryImpl()
        }

        fun getInstance(): AppRepository {
            return instance!!
        }
    }

    private val wordDao = MyDatabase.getInstance().wordDao()

    override fun getAllWords(): Cursor = wordDao.getAllWords()

    override fun getEnglishWordsByQuery(query: String): Cursor = wordDao.getEnglishWordsByQuery(query)

    override fun getUzbekWordsByQuery(query: String): Cursor = wordDao.getUzbekWordsByQuery(query)

    override fun updateWord(id : Long, isFavourite: Long) = wordDao.updateIsFavourite(id, isFavourite) // Unit

    override fun getWordById(id: Long)  = wordDao.getWordById(id)

    override fun getAllFavouriteWords(): Cursor = wordDao.getAllFavouriteWords()
    override fun removeFromFavourite(id: Long) = wordDao.removeFromFavourite(id)
}
package uz.gita.dictionary1.presentation.screens.main

import android.database.Cursor
import uz.gita.dictionary1.domain.AppRepository
import uz.gita.dictionary1.domain.AppRepositoryImpl

class MainModel : MainContract.Model {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override fun getAllWords(): Cursor {
        return repository.getAllWords()
    }

    override fun getEnglishWordsByQuery(query: String): Cursor {
        return repository.getEnglishWordsByQuery(query)
    }

    override fun getUzbekWordsByQuery(query: String): Cursor {
        return repository.getUzbekWordsByQuery(query)
    }

    override fun updateWord(id: Long, isFavourite: Long) = repository.updateWord(id, isFavourite)
}
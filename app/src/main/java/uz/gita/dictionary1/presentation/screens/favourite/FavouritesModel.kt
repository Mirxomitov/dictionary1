package uz.gita.dictionary1.presentation.screens.favourite

import android.database.Cursor
import uz.gita.dictionary1.domain.AppRepository
import uz.gita.dictionary1.domain.AppRepositoryImpl


class FavouritesModel : FavouritesContract.Model {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override fun removeFromFavourite(id: Long) {
        repository.removeFromFavourite(id)
    }

    override fun getAllFavouriteWords(): Cursor {
        return repository.getAllFavouriteWords()
    }
}
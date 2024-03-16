package uz.gita.dictionary1.presentation.screens.favourite

import java.util.concurrent.Executors

class FavouritesPresenter(private val view: FavouritesContract.View) : FavouritesContract.Presenter {
    private val model: FavouritesContract.Model = FavouritesModel()
    private val executor = Executors.newSingleThreadExecutor()

    override fun loadWords() {
        executor.execute {
            view.showWords(model.getAllFavouriteWords())
        }
    }

    override fun removeFromFavourite(id: Long) {
        executor.execute {
            model.removeFromFavourite(id)
        }
    }
}
package uz.gita.dictionary1.presentation.screens.main

import java.util.concurrent.Executors

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    private val model: MainContract.Model = MainModel()
    private val executor = Executors.newSingleThreadExecutor()

    override fun loadWords() {
        executor.execute {
            view.showWords(model.getAllWords())
        }
    }

    override fun loadWords(query: String, isEnToUz: Boolean) {
        executor.execute {
            view.showWords(
                if (isEnToUz) model.getEnglishWordsByQuery(query)
                else model.getUzbekWordsByQuery(query)
            )
        }
    }

    override fun updateWord(id: Long, isFavourite: Long) {
        executor.execute {
            model.updateWord(id, isFavourite)
        }
    }
}
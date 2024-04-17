package uz.gita.dictionary1.presentation.screens.add

import uz.gita.dictionary1.data.sources.entity.WordEntity
import java.util.concurrent.Executors

class AddPresenter(private val view: AddContract.View) : AddContract.Presenter {
    private val model: AddContract.Model = AddModel()
    private val executor = Executors.newSingleThreadExecutor()
    override fun addWord(wordEntity: WordEntity) {
        executor.execute {
            model.addWord(wordEntity)
        }
    }
}
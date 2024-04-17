package uz.gita.dictionary1.presentation.screens.add

import uz.gita.dictionary1.data.sources.entity.WordEntity
import uz.gita.dictionary1.domain.AppRepository
import uz.gita.dictionary1.domain.AppRepositoryImpl


class AddModel : AddContract.Model {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override fun addWord(wordEntity: WordEntity) {
        repository.addWord(wordEntity)
    }
}
package uz.gita.dictionary1.presentation.screens.add

import uz.gita.dictionary1.data.sources.entity.WordEntity

interface AddContract {
    interface View {

    }
    interface Model {
        fun addWord(wordEntity: WordEntity)
    }

    interface Presenter {
        fun addWord(wordEntity: WordEntity)
    }
}
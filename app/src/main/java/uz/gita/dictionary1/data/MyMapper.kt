package uz.gita.dictionary1.data

import uz.gita.dictionary1.data.model.WordData
import uz.gita.dictionary1.data.sources.entity.WordEntity

object MyMapper {
    fun WordEntity.toData(): WordData =
        WordData(
            english = english,
            type = type,
            transcript = transcript,
            uzbek = uzbek,
            isFavourite = isFavourite
        )
}
package uz.gita.dictionary1.data.model

import java.io.Serializable

data class WordData(
    val english: String,
    val type: String,
    val transcript: String,
    val uzbek: String,
    val isFavourite : Long
) : Serializable
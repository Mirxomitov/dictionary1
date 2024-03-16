package uz.gita.dictionary1.data.sources.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dictionary")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val english: String,
    val type: String,
    val transcript: String,
    val uzbek: String,
    val countable: String,
    @ColumnInfo("is_favourite") val isFavourite: Long = 0L,
) : Serializable

/*
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val english: String,
    val type: String,
    val transcript: String,
    val uzbek: String,
    val countable: String,
    val is_favourite: Int

 */
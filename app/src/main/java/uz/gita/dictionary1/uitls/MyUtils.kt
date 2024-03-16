package uz.gita.dictionary1.uitls

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.os.bundleOf
import uz.gita.dictionary1.data.sources.entity.WordEntity
import uz.gita.dictionary1.presentation.screens.main.MainScreen

fun String.createSpannable(query: String): SpannableString {
    val spannable = SpannableString(this)
    val startPos = this.indexOf(query)
    val endPos = startPos + query.length
    if (startPos < 0 || endPos > this.length)
        return spannable
    spannable.setSpan(
        ForegroundColorSpan(Color.parseColor("#1C2CC9")),
        startPos,
        endPos,
        SpannableString.SPAN_EXCLUSIVE_INCLUSIVE,
    )

    return spannable
}

@SuppressLint("Range")
fun Cursor.wordEntityByPos(position: Int): WordEntity {
    this.let {
        it.moveToPosition(position)
        return WordEntity(
            english = it.getString(it.getColumnIndex("english")),
            uzbek = it.getString(it.getColumnIndex("uzbek")),
            type = it.getString(it.getColumnIndex("type")),
            transcript = it.getString(it.getColumnIndex("transcript")),
            countable = it.getString(it.getColumnIndex("countable")),
            isFavourite = it.getLong(it.getColumnIndex("is_favourite"))
        )
    }
}
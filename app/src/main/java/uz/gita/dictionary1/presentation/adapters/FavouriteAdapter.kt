package uz.gita.dictionary1.presentation.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary1.data.MyMapper.toData
import uz.gita.dictionary1.data.model.WordData
import uz.gita.dictionary1.data.sources.entity.WordEntity
import uz.gita.dictionary1.databinding.ItemDictionaryBinding
import uz.gita.dictionary1.uitls.createSpannable
import uz.gita.dictionary1.uitls.wordEntityByPos

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.WordViewHolder>() {
    private var cursor: Cursor? = null
    private var query: String? = null
    private var onFavouriteClickListener: ((Long, Long) -> Unit)? = null
    private var onItemClickListener: ((WordEntity) -> Unit)? = null

    @SuppressLint("Range")
    inner class WordViewHolder(private val binding: ItemDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: WordData, id: Long) {

            if (query == null) binding.txtWord.text = data.english
            else binding.txtWord.text = data.english.createSpannable(query!!)

            binding.txtTranslation.text = data.uzbek

            binding.type.text = "[${data.type}]"


            binding.icFavourite.isVisible = data.isFavourite == 1L
            binding.icNotFavourite.isVisible = data.isFavourite == 0L

            binding

        }

        init {
            binding.apply {
                icFavourite.setOnClickListener {
                    cursor?.let {
                        it.moveToPosition(absoluteAdapterPosition)
                        val id = it.getLong(it.getColumnIndex("id"))
                        onFavouriteClickListener?.invoke(
                            id, 0L
                        )
                    }
                }
            }

            binding.root.setOnClickListener {
                cursor?.let {
                    it.moveToPosition(absoluteAdapterPosition)
                    onItemClickListener?.invoke(it.wordEntityByPos(absoluteAdapterPosition))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordViewHolder(
        ItemDictionaryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = if (cursor == null) 0 else cursor!!.count

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        cursor?.let {
            it.moveToPosition(position)
            val wordEntity = WordEntity(
                english = it.getString(it.getColumnIndex("english")),
                uzbek = it.getString(it.getColumnIndex("uzbek")),
                type = it.getString(it.getColumnIndex("type")),
                transcript = it.getString(it.getColumnIndex("type")),
                countable = it.getString(it.getColumnIndex("type")),
                isFavourite = it.getLong(it.getColumnIndex("is_favourite"))
            )

            val id = it.getLong(it.getColumnIndex("id"))
            holder.bind(wordEntity.toData(), id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

    fun setOnFavouriteClickListener(block: (Long, Long) -> Unit) {
        onFavouriteClickListener = block
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(block: (WordEntity) -> Unit) {
        onItemClickListener = block
    }
}
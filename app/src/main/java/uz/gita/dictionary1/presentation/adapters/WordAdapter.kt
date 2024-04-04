package uz.gita.dictionary1.presentation.adapters

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import uz.gita.dictionary1.data.MyMapper.toData
import uz.gita.dictionary1.data.model.WordData
import uz.gita.dictionary1.data.sources.entity.WordEntity
import uz.gita.dictionary1.databinding.ItemDictionaryBinding
import uz.gita.dictionary1.uitls.createSpannable
import uz.gita.dictionary1.uitls.wordEntityByPos

@Suppress("UNREACHABLE_CODE")
@SuppressLint("Range", "NotifyDataSetChanged", "SetTextI18n")
class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>(), FastScroller.SectionIndexer {
    private var cursor: Cursor? = null
    private var query: String? = null
    public fun getCursor() = cursor

    // id isFavouriteActive?
    private var onFavouriteClickListener: ((Long, Long) -> Unit)? = null
    private var onItemClickListener: ((WordEntity) -> Unit)? = null
    private var favouritesMap: HashMap<Long, Boolean> = HashMap()
    private var enToUz = true

    inner class WordViewHolder(private val binding: ItemDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: WordData, id: Long) {
            if (enToUz) {
                if (query == null) binding.txtWord.text = data.english
                else binding.txtWord.text =
                    data.english.createSpannable(query!!)

                binding.txtTranslation.text = data.uzbek
            } else {
                if (query == null) binding.txtWord.text = data.uzbek
                else binding.txtWord.text = data.uzbek.createSpannable(query!!)

                binding.txtTranslation.text = data.english
            }
            binding.type.text = "[${data.type}]"

            if (favouritesMap.containsKey(id)) {
                binding.icFavourite.isVisible = favouritesMap[id]!!
                binding.icNotFavourite.isVisible = !favouritesMap[id]!!
            } else {
                binding.icFavourite.isVisible = data.isFavourite == 1L
                binding.icNotFavourite.isVisible = data.isFavourite == 0L
            }
        }

        private fun changeFavourite(id: Long, isFavActive: Boolean) {
            favouritesMap[id] = isFavActive

            if (isFavActive) {
                binding.icFavourite.isVisible = true
                binding.icNotFavourite.isVisible = false
            } else {
                binding.icFavourite.isVisible = false
                binding.icNotFavourite.isVisible = true
            }

            onFavouriteClickListener?.invoke(
                id, if (isFavActive) 1L else 0L
            ) // is active make inactive vice versa
        }

        init {
            binding.apply {
                icFavourite.setOnClickListener {
                    cursor?.let {
                        it.moveToPosition(absoluteAdapterPosition)
                        val id = it.getLong(it.getColumnIndex("id"))
                        changeFavourite(id, false)
                    }
                }
            }

            binding.apply {
                icNotFavourite.setOnClickListener {
                    cursor?.let {
                        it.moveToPosition(absoluteAdapterPosition)
                        val id = it.getLong(it.getColumnIndex("id"))
                        changeFavourite(id, true)
                    }
                }
            }

            binding.root.setOnClickListener {
                cursor?.let {
                    onItemClickListener?.invoke(
                        it.wordEntityByPos(
                            absoluteAdapterPosition
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordViewHolder(
            ItemDictionaryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = if (cursor == null) 0 else cursor!!.count


    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        cursor?.let { cursor ->
            cursor.moveToPosition(position)
            val id = cursor.getLong(0)
            holder.bind(cursor.wordEntityByPos(position).toData(), id)
        }
    }

    fun setCursor(cursor: Cursor, query: String? = null) {
        this.cursor?.close()
        favouritesMap.clear()
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

    fun changeLanguage(isEnToUz: Boolean = false) {
        this.enToUz = isEnToUz
        notifyDataSetChanged()
    }

    fun setOnFavouriteClickListener(block: (Long, Long) -> Unit) {
        onFavouriteClickListener = block
    }

    fun setOnItemClickListener(block: (WordEntity) -> Unit) {
        onItemClickListener = block
    }

    override fun getSectionText(position: Int): CharSequence {
        return if (enToUz) {
            cursor?.wordEntityByPos(position)?.english ?: ""
        } else {
            cursor?.wordEntityByPos(position)?.uzbek ?: ""
        }
    }
}
package uz.gita.dictionary1.presentation.screens.favourite

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionary1.R
import uz.gita.dictionary1.databinding.ScreenFavouritesBinding
import uz.gita.dictionary1.presentation.adapters.FavouriteAdapter
import uz.gita.dictionary1.presentation.dialogs.details.DetailsDialog

class FavouritesScreen : Fragment(R.layout.screen_favourites),
    FavouritesContract.View {
    private val binding by viewBinding(ScreenFavouritesBinding::bind)
    private lateinit var adapter: FavouriteAdapter
    private lateinit var presenter: FavouritesContract.Presenter
    private var detailsDialog: DetailsDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FavouriteAdapter()
        adapter.setOnFavouriteClickListener { id, _ ->
            presenter.removeFromFavourite(id)
            presenter.loadWords()
        }

        adapter.setOnItemClickListener {
            detailsDialog = DetailsDialog()
            detailsDialog!!.arguments = bundleOf(Pair("data", it))
            detailsDialog!!.show(parentFragmentManager, "")
        }

        presenter = FavouritesPresenter(this)

        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            //childFragmentManager.popBackStack()
        }

        presenter.loadWords()
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter.setCursor(cursor)
            binding.placeHolder.isVisible = cursor.count == 0
        }
    }
}

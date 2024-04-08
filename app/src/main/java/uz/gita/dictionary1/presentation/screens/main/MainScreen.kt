package uz.gita.dictionary1.presentation.screens.main

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionary1.R
import uz.gita.dictionary1.databinding.ScreenMainNavBinding
import uz.gita.dictionary1.presentation.adapters.WordAdapter
import uz.gita.dictionary1.presentation.dialogs.details.DetailsDialog

class MainScreen : Fragment(R.layout.screen_main_nav), MainContract.View {

    private val drawerBinding by viewBinding(ScreenMainNavBinding::bind)
    private var adapter: WordAdapter? = null
    private val presenter: MainContract.Presenter by lazy { MainPresenter(this) }
    private var currentQuery: String? = null
    private var detailsDialog: DetailsDialog? = null
    private var isEngToUz = true
    private val REQ_CODE_SPEECH_INPUT = 100
    private var time = System.currentTimeMillis()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerBinding.inner.icRecorder.setOnClickListener {
            promptSpeechInput()
        }

        drawerBinding.navigationView

        initDrawer()
        initAdapter()
        searchViewSettings()
        drawerBinding.inner.btnChangeLanguage.setOnClickListener {
            if (System.currentTimeMillis() - time < 1000) return@setOnClickListener

            drawerBinding.inner.btnChangeLanguage.animate().rotationBy(180f).setDuration(1000)
            isEngToUz = !isEngToUz

            drawerBinding.inner.leftLanguageTv.text = if (isEngToUz) "English" else "O'zbek"
            drawerBinding.inner.rightLanguageTv.text = if (!isEngToUz) "Ingliz" else "Uzbek"

            presenter.loadWords(query = currentQuery ?: "", isEngToUz)
            adapter?.changeLanguage(isEngToUz)
            adapter?.setCursor(null, null)

            time = System.currentTimeMillis()
        }
        presenter.loadWords()
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        if (isEngToUz) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uz-Uz")
        }

        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                currentQuery = message?.get(0)
                drawerBinding.inner.searchView.setQuery(currentQuery, true)
            }
        }
    }

    fun updateResults(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
        drawerBinding.inner.searchView.setQuery(s, false)
    }

    private fun initDrawer() {
        drawerBinding.inner.btnMenu.setOnClickListener {
            drawerBinding.drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerBinding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.home -> {
                    drawerBinding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.user_favourites -> {
                    val direction =
                        MainScreenDirections.actionMainScreenToFavouritesScreen()
                    findNavController().navigate(direction)
                    drawerBinding.drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.rateUs -> {}

                R.id.aboutUs -> {
                    val direction =
                        MainScreenDirections.actionMainScreenToInfoScreen()
                    findNavController().navigate(direction)
                    drawerBinding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }


    private fun initAdapter() {
        adapter = WordAdapter()

        adapter?.setOnItemClickListener {
            detailsDialog = DetailsDialog()
            detailsDialog?.arguments = bundleOf(Pair("data", it))
            detailsDialog!!.show(parentFragmentManager, "")
        }

        adapter!!.setOnFavouriteClickListener { id, isChecked ->
            presenter.updateWord(id, isChecked)
        }

        drawerBinding.inner.wordList.apply {
            adapter = this@MainScreen.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun searchViewSettings() {
        drawerBinding.inner.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                drawerBinding.inner.wordList.scrollToPosition(0)
                currentQuery = newText
                if (currentQuery == null) presenter.loadWords()
                else presenter.loadWords(currentQuery!!, isEngToUz)

                return true
            }
        })

        // close button
        drawerBinding.inner.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            .setOnClickListener {
                drawerBinding.inner.searchView.setQuery(null, false)
                drawerBinding.inner.searchView.clearFocus()
            }
    }

    override fun showWords(cursor: Cursor) {
        requireActivity().runOnUiThread {
            adapter?.setCursor(cursor, currentQuery)
        }
    }
}


//            arguments = bundleOf(Pair("id", it.id))
//            arguments = bundleOf(Pair("countable", it.countable))
//            arguments = bundleOf(Pair("english", it.english))
//            arguments = bundleOf(Pair("uzbek", it.uzbek))
//            arguments = bundleOf(Pair("type", it.type))
//            arguments = bundleOf(Pair("isFavourite", it.isFavourite))
//            arguments = bundleOf(Pair("transcript", it.transcript))

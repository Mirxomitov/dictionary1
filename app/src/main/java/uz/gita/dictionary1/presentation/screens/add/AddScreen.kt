package uz.gita.dictionary1.presentation.screens.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionary1.R
import uz.gita.dictionary1.data.sources.entity.WordEntity
import uz.gita.dictionary1.databinding.ScreenAddWordBinding

class AddScreen : Fragment(R.layout.screen_add_word), AddContract.View {
    private val binding by viewBinding(ScreenAddWordBinding::bind)
    private val presenter: AddContract.Presenter = AddPresenter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.englishET.addTextChangedListener {
            if (it == null) return@addTextChangedListener
            if (it.toString().trim().isEmpty()) {
                binding.english.error = "Please, write word"
                binding.btnAddWord.isEnabled = false
            } else {
                binding.english.error = null
                if (binding.uzbekET.text.toString().trim().isNotEmpty()) binding.btnAddWord.isEnabled = true
            }
        }

        binding.uzbekET.addTextChangedListener {
            if (it == null) return@addTextChangedListener
            if (it.toString().trim().isEmpty()) {
                binding.uzbek.error = "Please, write word"
                binding.btnAddWord.isEnabled = false
            } else {
                binding.uzbek.error = null
                if (binding.englishET.text.toString().trim().isNotEmpty()) binding.btnAddWord.isEnabled = true
            }
        }

        binding.btnAddWord.setOnClickListener {
            if (binding.englishET.text.toString().trim().isEmpty()) {
                binding.english.error = "Please, write word"
                return@setOnClickListener
            }

            if (binding.uzbekET.text.toString().trim().isEmpty()) {
                binding.uzbek.error = "Please, write word"
                return@setOnClickListener
            }

            presenter.addWord(
                WordEntity(
                    id = 0,
                    english = binding.englishET.text.toString().trim(),
                    type = "",
                    transcript = "",
                    uzbek = binding.uzbekET.text.toString().trim(),
                    countable = "",
                    isFavourite = if (binding.isFavourite.isChecked) 1 else 0,
                )
            )

            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
        }
    }
}
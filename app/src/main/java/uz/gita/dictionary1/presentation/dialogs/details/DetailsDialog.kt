package uz.gita.dictionary1.presentation.dialogs.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.dictionary1.R
import uz.gita.dictionary1.data.sources.entity.WordEntity
import uz.gita.dictionary1.databinding.DialogDetailsBinding
import java.util.Locale

class DetailsDialog : DialogFragment(R.layout.dialog_details),
    TextToSpeech.OnInitListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog?.window?.setLayout(
            width,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        setStyle(STYLE_NO_FRAME, R.style.DialogStyle)
//        dialog?.window?.setBackgroundDrawable(null)
    }

    private val binding by viewBinding(DialogDetailsBinding::bind)
    private var buttonSpeak: ImageView? = null
    private var tts: TextToSpeech? = null

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtons()



        arguments?.let { args ->
            val data = args.getSerializable("data") as? WordEntity
            if (data != null) {
                showDetails(data)
                Log.d("TTT", "SHOW DETAILS")
            } else {
                Log.e("TTT", "Error: Data is null or not of type WordEntity")
            }
        }

    }

    private fun initButtons() {
        buttonSpeak = binding.btnSpeak
        binding.cancel.setOnClickListener {
            dismiss()
        }
        tts = TextToSpeech(requireActivity(), this)
        buttonSpeak!!.setOnClickListener { speakOut() }
    }

    private fun speakOut() {
        val text = binding.txtWord.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }
        }
    }

    private fun showDetails(wordEntity: WordEntity) {
        requireActivity().runOnUiThread {

            binding.apply {
                txtTranscription.text = wordEntity.transcript
                txtType.text = wordEntity.type
                txtTranslation.text = wordEntity.uzbek
                txtWord.text = wordEntity.english
                if (wordEntity.countable != "") {
                    txtCountable.isVisible = true
                    txtCountable.text = wordEntity.countable
                } else {
                    txtCountable.visibility = View.INVISIBLE
                }
            }
        }
    }
}

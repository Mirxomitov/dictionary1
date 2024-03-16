package uz.gita.dictionary1.presentation.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.gita.dictionary1.R

@SuppressLint("CustomSplashScreen")
class MySplashScreen : Fragment(R.layout.screen_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            val direction = MySplashScreenDirections.actionMySplashScreenToMainScreen()
            findNavController().navigate(direction)
        }, 500)
    }
}
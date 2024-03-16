package uz.gita.dictionary1.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.dictionary1.presentation.screens.favourite.FavouritesScreen

class DrawerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return FavouritesScreen()
    }
}
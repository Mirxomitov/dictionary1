package uz.gita.dictionary1.app

import android.app.Application
import uz.gita.dictionary1.data.sources.MyDatabase
import uz.gita.dictionary1.domain.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
        AppRepositoryImpl.init()
    }
}
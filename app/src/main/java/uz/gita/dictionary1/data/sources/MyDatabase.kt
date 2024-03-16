package uz.gita.dictionary1.data.sources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.dictionary1.data.sources.dao.WordDao
import uz.gita.dictionary1.data.sources.entity.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var instance: MyDatabase? = null

        fun init(context: Context) {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, MyDatabase::class.java, "dictionary")
                        .createFromAsset("database/dictionary.db")
                        .build()
            }
        }

        fun getInstance() = instance!!
    }
}
package ru.empat.morewords.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.LearnProgressDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.data.room.entity.DictionaryModel
import ru.empat.morewords.data.room.entity.LanguageModel
import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.data.room.entity.WordModel

@Database(
    entities = [WordModel::class, LanguageModel::class, DictionaryModel::class, LearningProgressWordModel::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun languageDao(): LanguageDao
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun learnProgressDao(): LearnProgressDao

    companion object {
        const val DATABASE_NAME = "user_database"

        private var INSTANCE: UserDatabase? = null

        private val LOCK = Any()

        fun newInstance(context: Context): UserDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                INSTANCE?.let { initDb(it) }
                            } catch (e: Exception) {
                                Log.e("Room", "Init error ${e.stackTrace}")
                            }
                        }
                    }
                }).build()

                INSTANCE = database
                return database
            }
        }

        private suspend fun initDb(instance: UserDatabase) {
            instance.languageDao().insert(LanguageModel(1, "English", "En"))
            instance.languageDao().insert(LanguageModel(1, "Русский", "Ru"))
            instance.dictionaryDao().addDictionaty(DictionaryModel(1, "User", 1))
        }
    }
}

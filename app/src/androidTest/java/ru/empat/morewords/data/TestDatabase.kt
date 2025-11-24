package ru.empat.morewords.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
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
abstract class TestDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    abstract fun languageDao() : LanguageDao

    abstract fun dictionaryDao() : DictionaryDao

//    abstract fun learnProgressDao() : LearnProgressDao

    companion object {
        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun getInMemoryInstance(context: Context) =
            Room.inMemoryDatabaseBuilder(
                context,
                TestDatabase::class.java
            ).allowMainThreadQueries().build()

    }
}
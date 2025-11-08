package ru.empat.morewords.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.empat.morewords.data.TestDatabase
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.data.room.entity.DictionaryModel
import ru.empat.morewords.data.room.entity.LanguageModel
import ru.empat.morewords.data.room.entity.WordModel

class WordDaoTest {
    private lateinit var database: TestDatabase
    private lateinit var wordDao: WordDao
    private lateinit var languageDao: LanguageDao
    private lateinit var dictionaryDao: DictionaryDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = TestDatabase.getInMemoryInstance(context)

        wordDao = database.wordDao()
        languageDao = database.languageDao()
        dictionaryDao = database.dictionaryDao()
    }

    @After
    fun clear(){
        database.close()
    }

    @Test
    fun insert(){
        val languageModel = LanguageModel(1, "Английски", "En")
        languageDao.insert(languageModel)

        val dictionaryModel = DictionaryModel(1, "User", 1)
        dictionaryDao.addDictionaty(dictionaryModel)

        val wordModel = WordModel(1 , 1, "Hello" , "Привет")
        wordDao.insertWord(wordModel)

        val actual: WordModel = wordDao.getWordById(1)!!

        Assert.assertEquals(wordModel, actual)
    }
}
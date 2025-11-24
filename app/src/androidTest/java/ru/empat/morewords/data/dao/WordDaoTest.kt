package ru.empat.morewords.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.data.room.entity.WordModel
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word
import java.util.Date

class WordDaoTest {
    private lateinit var database: TestDatabase
    private lateinit var wordDao: WordDao
    private lateinit var languageDao: LanguageDao
    private lateinit var dictionaryDao: DictionaryDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = TestDatabase.getInMemoryInstance(context)

        wordDao = database.wordDao()
        languageDao = database.languageDao()
        dictionaryDao = database.dictionaryDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun insert() = runTest {
        val languageModel = LanguageModel(1, "English" , "En")
        val dictionaryModel = DictionaryModel(1, "Name" , 1)

        languageDao.insert(languageModel)
        dictionaryDao.addDictionaty(dictionaryModel)

        val learn = LearningProgressWordModel(1, 1, 1L, 1L, true)
        val wordModel = WordModel(1, 1, "1", "s", learn)

        wordDao.insertWord(wordModel)

        val actual: WordModel = wordDao.getAllWords().first()!![0]

        Assert.assertEquals(wordModel, actual)
    }
}
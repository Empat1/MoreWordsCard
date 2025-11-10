package ru.empat.morewords.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.empat.morewords.data.TestDatabase
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.LearnProgressDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.data.room.entity.DictionaryModel
import ru.empat.morewords.data.room.entity.LanguageModel
import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.data.room.entity.WordModel

class LearnProgressDaoTest {

    private lateinit var database: TestDatabase
    private lateinit var wordDao: WordDao
    private lateinit var languageDao: LanguageDao
    private lateinit var dictionaryDao: DictionaryDao
    private lateinit var learnDao : LearnProgressDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = TestDatabase.getInMemoryInstance(context)

        wordDao = database.wordDao()
        languageDao = database.languageDao()
        dictionaryDao = database.dictionaryDao()
        learnDao = database.learnProgressDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun insert() = runTest {
        val languageModel = LanguageModel(1, "Английски", "En")
        languageDao.insert(languageModel)

        val dictionaryModel = DictionaryModel(1, "User", 1)
        dictionaryDao.addDictionaty(dictionaryModel)

        val wordModel = WordModel(1, 1, "Hello", "Привет")
        wordDao.insertWord(wordModel)

        val learningProgressWordModel = LearningProgressWordModel(1, 1, 0, 1 , 2)
        learnDao.insert(learningProgressWordModel)

        val actual = learnDao.getLearn(1)

        Assert.assertEquals(learningProgressWordModel, actual)
    }

    @Test
    fun update() = runTest {
        insert()
        val learn = LearningProgressWordModel(1 , 1, 4, 4, 5)
        learnDao.edit(learn)

        Assert.assertEquals(learn, learnDao.getLearn(1))
    }

    @Test
    fun delete() = runTest{
        insert()
        learnDao.delete(1)

        Assert.assertNull(learnDao.getLearn(1))
    }
}
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
import ru.empat.morewords.data.room.dao.LearnProgressDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.data.room.entity.Dictionary
import ru.empat.morewords.data.room.entity.Language
import ru.empat.morewords.data.room.entity.LearningProgressWord
import ru.empat.morewords.data.room.entity.Word

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
    fun insert() {
        val language = Language(1, "Английски", "En")
        languageDao.insert(language)

        val dictionary = Dictionary(1, "User", 1)
        dictionaryDao.addDictionaty(dictionary)

        val word = Word(1, 1, "Hello", "Привет")
        wordDao.insertWord(word)

        val learningProgressWord = LearningProgressWord(1, 1, 0, 1 , 2)
        learnDao.insert(learningProgressWord)

        val actual = learnDao.getLearn(1)

        Assert.assertEquals(learningProgressWord, actual)
    }

    @Test
    fun update() {
        insert()
        val learn = LearningProgressWord(1 , 1, 4, 4, 5)
        learnDao.edit(learn)

        Assert.assertEquals(learn, learnDao.getLearn(1))
    }

    @Test
    fun delete() {
        insert()
        learnDao.delete(1)

        Assert.assertNull(learnDao.getLearn(1))
    }
}
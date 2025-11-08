package ru.empat.morewords.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.empat.morewords.data.TestDatabase
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.entity.Language

class LanguageDaoTest {

    private lateinit var database: TestDatabase
    private lateinit var languageDao: LanguageDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = TestDatabase.getInMemoryInstance(context)

        languageDao = database.languageDao()
    }

    @After
    fun clear(){
        database.close()
    }

    @Test
    fun insertAndFindTest(){
        val language = Language(1, "Английски", "En")
        languageDao.insert(language)

        val actual: Language = languageDao.getLanguage(1)

        Assert.assertEquals(actual, actual)
    }

}
package ru.empat.morewords.data.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Database
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.empat.morewords.data.TestDatabase
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.entity.Dictionary
import ru.empat.morewords.data.room.entity.Language

class DictionaryDaoTest {
    private lateinit var database: TestDatabase
    private lateinit var languageDao: LanguageDao
    private lateinit var dictionaryDao: DictionaryDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = TestDatabase.getInMemoryInstance(context)

        languageDao = database.languageDao()
        dictionaryDao = database.dictionaryDao()
    }

    @After
    fun clear(){
        database.close()
    }

    @Test
    fun insertAndFindTest(){
        val language = Language(1, "Английски", "En")
        languageDao.insert(language)

        val dictionary = Dictionary(1, "User", 1)
        dictionaryDao.addDictionaty(dictionary)

        val actual = dictionaryDao.getDictionary(1)

        Assert.assertEquals(dictionary, actual)
    }

    @Test
    fun insertWithoutLanguage(){
        val dictionary = Dictionary(1, "User", 1)


        val actual = try {
            dictionaryDao.addDictionaty(dictionary)
            false
        } catch (e: SQLiteConstraintException){
            true
        }
        assert(actual)
    }
}
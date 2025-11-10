package ru.empat.morewords.data.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.empat.morewords.data.TestDatabase
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.entity.DictionaryModel
import ru.empat.morewords.data.room.entity.LanguageModel

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
    fun insertAndFindTest() = runTest{
        val languageModel = LanguageModel(1, "Английски", "En")
        languageDao.insert(languageModel)

        val dictionaryModel = DictionaryModel(1, "User", 1)
        dictionaryDao.addDictionaty(dictionaryModel)

        val actual = dictionaryDao.getDictionary(1)

        Assert.assertEquals(dictionaryModel, actual)
    }

    @Test
    fun insertWithoutLanguage() = runTest{
        val dictionaryModel = DictionaryModel(1, "User", 1)

        val actual = try {
            dictionaryDao.addDictionaty(dictionaryModel)
            false
        } catch (e: SQLiteConstraintException){
            true
        }
        assert(actual)
    }
}
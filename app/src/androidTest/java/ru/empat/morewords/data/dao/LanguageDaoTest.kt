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
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.entity.LanguageModel

class LanguageDaoTest {

    private lateinit var database: TestDatabase
    private lateinit var languageDao: LanguageDao

    @Before
    fun setup() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = TestDatabase.getInMemoryInstance(context)

        languageDao = database.languageDao()
    }

    @After
    fun clear() {
        database.close()
    }

    @Test
    fun insertAndFindTest() = runTest {
        val languageModel = LanguageModel(1, "Английски", "En")
        languageDao.insert(languageModel)

        val actual: LanguageModel = languageDao.getLanguage(1).first()

        Assert.assertEquals(actual, actual)
    }

}
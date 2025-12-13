package ru.empat.morewords.data.room.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.data.room.mappers.asExternalModel
import ru.empat.morewords.data.room.mappers.toDbModel
import ru.empat.morewords.data.room.mappers.toLanguage
import ru.empat.morewords.data.room.mappers.toLanguageModel
import ru.empat.morewords.data.room.mappers.toWordModel
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import java.util.Date
import javax.inject.Inject
import kotlin.collections.map

class WorkRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val languageDao: LanguageDao
) : WordRepository {

    override suspend fun addWord(word: Word): Long {
        return wordDao.insertWord(word.toWordModel())
    }

    override suspend fun editWord(word: Word) {
        wordDao.updateWord(word.toWordModel())
    }

    override suspend fun removeWord(id: Long) {
        wordDao.deleteWord(id)
    }

    override fun getDictionaryWorld(id: Long): Flow<List<Word>> {
        return wordDao.getWordByDictionary(id)
            .map { wordModels -> wordModels.map { it.asExternalModel() } }
    }

    override fun getAllLanguages(): Flow<List<Language>> {
        return languageDao.getAllLanguages()
            .map { languageModel -> languageModel.map { it.toLanguage() } }
    }

    override suspend fun addLanguages(language: Language) {
        languageDao.insert(language.toLanguageModel())
    }

    override fun getWordForRepeat(limit: Long): Flow<List<Word>> {
        return wordDao.getOldRepeatedWord(limit)
            .map { list ->
                list
                    ?.map { it.asExternalModel() }
                    ?: emptyList()
            }.catch {
                it
            }
    }

    override fun getAllWords(): Flow<List<Word>> {
        return wordDao.getAllWords()
            .map { list ->
                list?.map { it.asExternalModel() } ?: emptyList()
            }
    }

    override fun getWordById(id: Long): Flow<Word?> {
        return wordDao.getWordById(id).map { it?.asExternalModel() }
    }
}
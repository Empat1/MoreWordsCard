package ru.empat.morewords.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.LearnProgressDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.data.room.mappers.toLanguage
import ru.empat.morewords.data.room.mappers.toLanguageModel
import ru.empat.morewords.data.room.mappers.toLearningProgressWordModel
import ru.empat.morewords.data.room.mappers.toWord
import ru.empat.morewords.data.room.mappers.toWordModel
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class WorkRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val languageDao: LanguageDao,
    private val learnProgressDao: LearnProgressDao
) : WordRepository {

    override suspend fun addWord(word: Word) : Long {
        learnProgressDao.insert(LearningProgressWordModel(
            id = 0,
            wordId = word.wordId,
            knowledgeLevel = 0,
            lastReviewed = null,
            nextReview = 0
        ))
        return wordDao.insertWord(word.toWordModel())
    }

    override suspend fun editWord(word: Word) {
        wordDao.updateWord(word.toWordModel())
    }

    override suspend fun removeWord(id: Long) {
        wordDao.deleteWord(id)
    }

    override fun getDictionaryWorld(id: Long): Flow<List<Word>> {
        return wordDao.getWordByDictionary(id).map{ wordModels -> wordModels.map{it.toWord()}}
    }

    override fun getAllLanguages(): Flow<List<Language>> {
        return languageDao.getAllLanguages()
            .map { languageModel -> languageModel.map { it.toLanguage() } }
    }

    override suspend fun addLanguages(language: Language) {
        languageDao.insert(language.toLanguageModel())
    }

    override suspend fun learnWord(learn: Learn) {
        learnProgressDao.insert(learn.toLearningProgressWordModel())
    }
}
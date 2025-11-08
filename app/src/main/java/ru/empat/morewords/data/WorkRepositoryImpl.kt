package ru.empat.morewords.data

import ru.empat.morewords.data.room.dao.DictionaryDao
import ru.empat.morewords.data.room.dao.LanguageDao
import ru.empat.morewords.data.room.dao.LearnProgressDao
import ru.empat.morewords.data.room.dao.WordDao
import ru.empat.morewords.domain.entity.Dictionary
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class WorkRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val languageDao: LanguageDao,
    private val learnProgressDao: LearnProgressDao,
    private val dictionaryDao: DictionaryDao
) : WordRepository {

    override fun addWord(word: Word) {
        TODO("Not yet implemented")
    }

    override fun editWord(word: Word) {
        TODO("Not yet implemented")
    }

    override fun removeWord(word: Word) {
        TODO("Not yet implemented")
    }

    override fun getDictionaryWorld(id: Dictionary): List<Dictionary> {
        TODO("Not yet implemented")
    }

    override fun getLanguages(id: Long): Language {
        TODO("Not yet implemented")
    }

    override fun learnWord(learn: Learn) {
        TODO("Not yet implemented")
    }
}
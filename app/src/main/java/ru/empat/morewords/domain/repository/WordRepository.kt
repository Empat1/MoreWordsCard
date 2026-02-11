package ru.empat.morewords.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word

interface WordRepository {

    suspend fun addWord(word: Word): Long

    suspend fun editWord(word: Word)

    suspend fun removeWord(id: Long)

    fun getDictionaryWorld(id: Long): Flow<List<Word>>

    fun getAllLanguages(): Flow<List<Language>>

    suspend fun addLanguages(language: Language)

    fun getWordForRepeat(limit: Long): Flow<List<Word>>

    fun getAllWords(): Flow<List<Word>>

    fun getWordById(id: Long): Flow<Word?>

    fun getWorld(text: String): Flow<Word?>
}
package ru.empat.morewords.domain.repository

import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.entity.Word

interface WordRepository {

    fun addWord(word: Word)

    fun editWord(word: Word)

    fun removeWord(word: Word)

    fun getLanguages(id: Long) : Language
}
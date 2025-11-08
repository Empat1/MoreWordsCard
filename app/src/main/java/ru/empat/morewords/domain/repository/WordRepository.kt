package ru.empat.morewords.domain.repository

import ru.empat.morewords.domain.entity.Dictionary
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word

interface WordRepository {

    fun addWord(word: Word)

    fun editWord(word: Word)

    fun removeWord(word: Word)

    fun getDictionaryWorld(id: Dictionary) : List<Dictionary>

    fun getLanguages(id: Long) : Language

    fun learnWord(learn: Learn)
}
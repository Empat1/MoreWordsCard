package ru.empat.morewords.domain.entity

data class Word(
    var wordId: Int,
    var dictionaryId: Int,
    var text: String,
    var translate: String
)


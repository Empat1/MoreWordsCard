package ru.empat.morewords.domain.entity

data class Word(
    var wordId: Long,
    var dictionaryId: Long,
    var text: String,
    var translate: String
)


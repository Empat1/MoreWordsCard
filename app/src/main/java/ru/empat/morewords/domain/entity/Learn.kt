package ru.empat.morewords.domain.entity

import java.util.Date

data class Learn(
    var id: Long,
    var wordId: Long,
    val knowledgeLevel: Int = 0,
    var learnGoodRepetition: Date,
    var learnLastRepetition: Date?
)

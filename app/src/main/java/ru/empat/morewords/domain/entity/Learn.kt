package ru.empat.morewords.domain.entity

import java.sql.Date

data class Learn(
    var id: Int,
    var userId: Int,
    var wordId: Int,
    var learnGoodRepetition: Int,
    var learnLastRepetition: Date
)

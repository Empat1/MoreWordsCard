package ru.empat.morewords.domain.entity

import androidx.room.Embedded
import java.util.Date

data class Learn(
    var learnId: Long,
    val knowledgeLevel: Int = 0,
    var learnGoodRepetition: Date,
    var learnLastRepetition: Date?,
    var success: Boolean
)

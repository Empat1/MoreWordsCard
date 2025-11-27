package ru.empat.morewords.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Learn(
    var learnId: Long,
    val knowledgeLevel: Int = 0,
    var learnGoodRepetition: Date,
    var learnLastRepetition: Date?,
    var success: Boolean
) : Parcelable

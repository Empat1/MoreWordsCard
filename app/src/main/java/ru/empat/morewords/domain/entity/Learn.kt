package ru.empat.morewords.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Learn(
    val learnId: Long,
    val knowledgeLevel: Int = 0,
    val learnGoodRepetition: Date,
    val learnLastRepetition: Date,
    val isOpposite: Boolean,
    val success: Boolean
) : Parcelable

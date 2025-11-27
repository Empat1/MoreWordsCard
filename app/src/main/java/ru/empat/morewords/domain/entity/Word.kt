package ru.empat.morewords.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    var wordId: Long,
    var dictionaryId: Long,
    var text: String,
    var translate: String,
    var learn: Learn
) : Parcelable


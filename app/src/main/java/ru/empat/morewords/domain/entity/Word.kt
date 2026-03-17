package ru.empat.morewords.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val wordId: Long,
    val dictionaryId: Long,
    val text: String,
    val translate: String,
    val learn: Learn
) : Parcelable


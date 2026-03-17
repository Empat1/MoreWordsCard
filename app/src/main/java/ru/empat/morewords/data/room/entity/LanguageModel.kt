package ru.empat.morewords.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
class LanguageModel(
    @PrimaryKey
    val id: Long = 0
)
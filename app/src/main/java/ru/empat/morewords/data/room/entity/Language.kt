package ru.empat.morewords.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
class Language(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val code: String
)
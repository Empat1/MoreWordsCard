package ru.empat.morewords.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "dictionary",
    foreignKeys = [
        ForeignKey(
            entity = LanguageModel::class,
            parentColumns = ["id"],
            childColumns = ["languageId"],
        )
    ]
)
data class DictionaryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val languageId: Long,
)
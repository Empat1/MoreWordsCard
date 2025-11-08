package ru.empat.morewords.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word",
    foreignKeys = [
        ForeignKey(
            entity = DictionaryModel::class,
            parentColumns = ["id"],
            childColumns = ["dictionaryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dictionaryId: Long,
    val text: String,
    val translation: String
)
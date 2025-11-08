package ru.empat.morewords.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "learning_progress",
    indices = [Index(value = ["wordId"])],
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LearningProgressWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val wordId: Long,
    val knowledgeLevel: Int = 0,
    val lastReviewed: Long? = null,
    val nextReview: Long
)

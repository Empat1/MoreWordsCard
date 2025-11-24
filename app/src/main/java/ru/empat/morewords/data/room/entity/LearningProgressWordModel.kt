package ru.empat.morewords.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LearningProgressWordModel(
    @PrimaryKey(autoGenerate = true) val learnId: Long = 0,
    val knowledgeLevel: Int = 0,
    val lastReviewed: Long? = null,
    val nextReview: Long,
    val success: Boolean
)

package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.domain.entity.Learn
import java.util.Date

fun Learn.toLearningProgressWordModel(wordId: Long): LearningProgressWordModel = LearningProgressWordModel(
    id = id,
    wordId = wordId,
    knowledgeLevel = knowledgeLevel,
    lastReviewed = learnLastRepetition?.time,
    nextReview = learnGoodRepetition.time
)

fun LearningProgressWordModel.toLearn(userId: Long): Learn = Learn(
    id = id,
    userId = userId,
    knowledgeLevel = knowledgeLevel,
    learnGoodRepetition = Date(nextReview),
    learnLastRepetition = lastReviewed?.let { Date(it) }
)
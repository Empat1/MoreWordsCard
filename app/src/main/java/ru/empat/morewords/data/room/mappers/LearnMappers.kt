package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.domain.entity.Learn
import java.util.Date

fun Learn.toLearningProgressWordModel(): LearningProgressWordModel = LearningProgressWordModel(
    id = id,
    wordId = wordId,
    knowledgeLevel = knowledgeLevel,
    lastReviewed = learnLastRepetition?.time,
    nextReview = learnGoodRepetition.time
)

fun LearningProgressWordModel.toLearn(): Learn = Learn(
    id = id,
    wordId = wordId,
    knowledgeLevel = knowledgeLevel,
    learnGoodRepetition = Date(nextReview),
    learnLastRepetition = lastReviewed?.let { Date(it) }
)
package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.domain.entity.Learn
import java.util.Date

fun Learn.toDbModel(): LearningProgressWordModel = LearningProgressWordModel(
    learnId = learnId,
    knowledgeLevel = knowledgeLevel,
    lastReviewed = learnLastRepetition.time,
    nextReview = learnGoodRepetition.time,
    isOpposite = isOpposite,
    success = success
)

fun LearningProgressWordModel.asExternalModel(): Learn = Learn(
    learnId = learnId,
    knowledgeLevel = knowledgeLevel,
    learnGoodRepetition = Date(lastReviewed),
    learnLastRepetition = Date(nextReview),
    isOpposite = isOpposite,
    success = success
)
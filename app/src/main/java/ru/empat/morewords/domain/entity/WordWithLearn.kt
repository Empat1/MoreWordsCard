package ru.empat.morewords.domain.entity

import ru.empat.morewords.data.room.entity.LearningProgressWordModel
import ru.empat.morewords.data.room.entity.WordModel

class WordWithLearn(
    val word: Word,
    val learn: Learn
)
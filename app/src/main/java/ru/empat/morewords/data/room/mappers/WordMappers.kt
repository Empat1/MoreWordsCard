package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.WordModel
import ru.empat.morewords.domain.entity.Word

fun WordModel.asExternalModel(): Word = Word(
    wordId = id,
    dictionaryId = dictionaryId,
    text = text,
    translate = translation,
    learn = learnModel.asExternalModel()
)

fun Word.toWordModel(): WordModel = WordModel(
    id = wordId,
    dictionaryId = dictionaryId,
    text = text,
    translation = translate,
    learnModel = learn.toDbModel()
)
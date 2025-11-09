package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.DictionaryModel
import ru.empat.morewords.domain.entity.Dictionary

fun DictionaryModel.toDictionary(): Dictionary = Dictionary(
    id = id,
    language = languageId,
    name = name
)

fun Dictionary.toDictionaryModel(): DictionaryModel = DictionaryModel(
    id = id,
    name = name,
    languageId = language
)
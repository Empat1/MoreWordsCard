package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.LanguageModel
import ru.empat.morewords.domain.entity.Language

fun LanguageModel.toLanguage(): Language = Language(
    id = id,
    name = name,
    code = code
)

fun Language.toLanguageModel(): LanguageModel = LanguageModel(
    id = id,
    name = name,
    code = code
)
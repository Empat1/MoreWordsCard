package ru.empat.morewords.data.room.mappers

import ru.empat.morewords.data.room.entity.LanguageModel
import ru.empat.morewords.domain.entity.Language

fun LanguageModel.toLanguage(): Language =
    Language.entries.find { it.id == id }
        ?: throw IllegalArgumentException("Can't find language")


fun Language.toLanguageModel(): LanguageModel = LanguageModel(
    id = id
)
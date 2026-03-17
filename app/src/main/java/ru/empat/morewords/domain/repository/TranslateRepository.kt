package ru.empat.morewords.domain.repository

import ru.empat.morewords.domain.entity.Language

interface TranslateRepository {
    fun saveSetting(
        wordLanguage: Language, translateLanguage: Language
    )

    fun translate(text: String, result: (String) -> Unit)
    fun close()
}
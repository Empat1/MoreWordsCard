package ru.empat.morewords.domain.usecase

import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import java.util.Date
import javax.inject.Inject

class AddWordUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(test: String, translate: String) {
        val learn = Learn(0, 0, Date(), null, true)
        val word = Word(0, 1L, test, translate, learn)
        repository.addWord(word)
    }
}
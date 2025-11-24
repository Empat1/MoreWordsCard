package ru.empat.morewords.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.entity.WordWithLearn
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class GetOldRepeatedWordUseCase @Inject constructor(
    private val repository: WordRepository
) {
    operator fun invoke(): Flow<Word?> {
        return repository.getWordForRepeat(WORD_LIMIT).map { it.firstOrNull() }
    }

    companion object {
        const val WORD_LIMIT = 5L
    }
}
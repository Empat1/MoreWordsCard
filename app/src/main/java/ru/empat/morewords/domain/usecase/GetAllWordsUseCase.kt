package ru.empat.morewords.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class GetAllWordsUseCase @Inject constructor(
    val repository: WordRepository
) {
    operator fun invoke(): Flow<List<Word>> {
        return repository.getAllWords()
    }
}
package ru.empat.morewords.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class GetCountAllWordsUseCase @Inject constructor(
    val repository: WordRepository
) {

    operator fun invoke() : Flow<Int> {
        return repository.getCountAllWord()
    }
}
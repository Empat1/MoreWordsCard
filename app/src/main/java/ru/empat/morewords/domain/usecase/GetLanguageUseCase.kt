package ru.empat.morewords.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    val repository: WordRepository
) {
    operator fun invoke() : Flow<List<Language>> {
        return repository.getAllLanguages()
    }
}
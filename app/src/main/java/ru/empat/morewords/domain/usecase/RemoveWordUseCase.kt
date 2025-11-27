package ru.empat.morewords.domain.usecase

import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class RemoveWordUseCase @Inject constructor(
    private val repository: WordRepository
) {

    suspend operator fun invoke(wordId: Long){
        repository.removeWord(wordId)
    }
}
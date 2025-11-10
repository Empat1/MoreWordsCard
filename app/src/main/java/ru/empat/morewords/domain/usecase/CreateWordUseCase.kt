package ru.empat.morewords.domain.usecase

import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject

class CreateWordUseCase @Inject constructor(
    private val repository: WordRepository
){
    suspend operator fun invoke(word: Word){
        repository.addWord(word)
    }
}
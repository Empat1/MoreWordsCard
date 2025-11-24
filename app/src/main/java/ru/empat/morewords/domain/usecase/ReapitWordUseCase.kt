package ru.empat.morewords.domain.usecase

import kotlinx.coroutines.flow.firstOrNull
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.repository.WordRepository
import java.util.Date
import javax.inject.Inject

class RepeatWordUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(wordId: Long, success: Boolean) {
        val word = repository.getWordById(wordId).firstOrNull() ?: return

        val learn = if (success) {
            Learn(
                learnId = word.wordId,
                knowledgeLevel = word.learn.knowledgeLevel + 1,
                learnGoodRepetition = Date(),
                learnLastRepetition = Date(),
                success = true
            )
        } else {
            Learn(
                learnId = word.wordId,
                knowledgeLevel = word.learn.knowledgeLevel,
                learnGoodRepetition = word.learn.learnGoodRepetition,
                learnLastRepetition = Date(),
                success = false
            )
        }

        word.learn = learn

        repository.editWord(word)
    }
}
package ru.empat.morewords.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.empat.morewords.domain.entity.Word
import ru.empat.morewords.domain.repository.WordRepository
import javax.inject.Inject
import kotlin.math.min
import kotlin.random.Random

class GetOldRepeatedWordUseCase @Inject constructor(
    private val repository: WordRepository
) {
    var nextWord: Word? = null

    operator fun invoke(): Flow<Result?> {
        return repository
            .getWordForRepeat(WORD_LIMIT)
            .map { words ->
                if (words.isEmpty()) return@map null
                if (words.size == 1) return@map Result(words[0], null, 1)

                if (nextWord == null) {
                    nextWord = getRandomValue(words)
                    val word = getUniqueWord(words, nextWord!!)

                    return@map Result(word, nextWord, words.size)
                } else {
                    val word = nextWord!!
                    nextWord = getUniqueWord(words, word)

                    return@map Result(word, nextWord, words.size)
                }
            }
    }

    fun getUniqueWord(list: List<Word>, word: Word): Word {
        var unique: Word?
        do {
            unique = getRandomValue(list)
        } while (unique == word)
        return unique!!
    }

    fun getRandomValue(list: List<Word>): Word {
        val randomMax = min(list.size, WORD_LIMIT.toInt() - 1)
        val randomValue = Random.nextInt(randomMax)
        return list[randomValue]
    }

    companion object {
        const val WORD_LIMIT = 5L
    }

    data class Result(
        val word: Word,
        val nextWord: Word?,
        val loadedWords: Int
    )
}
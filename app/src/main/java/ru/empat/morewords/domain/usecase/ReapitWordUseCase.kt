package ru.empat.morewords.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.firstOrNull
import ru.empat.morewords.domain.entity.Learn
import ru.empat.morewords.domain.repository.WordRepository
import java.util.Date
import javax.inject.Inject
import kotlin.math.pow

class LearnWordUseCase @Inject constructor(
    private val repository: WordRepository
) {
    suspend operator fun invoke(wordId: Long, success: Boolean) {
        val word = repository.getWordById(wordId).firstOrNull() ?: return

        val learn = if (success) {

            if(word.learn.isOpposite){
                val nextRepeatTime = getRepeatTime(word.learn.knowledgeLevel)

                Learn(
                    learnId = word.wordId,
                    knowledgeLevel = word.learn.knowledgeLevel + 1,
                    learnGoodRepetition = Date(),
                    learnLastRepetition = Date(nextRepeatTime),
                    isOpposite = false,
                    success = true
                )
            }else{
                word.learn.copy(
                    isOpposite = true,
                    learnLastRepetition = Date()
                )
            }
        } else {
            word.learn.copy(
                knowledgeLevel = 1,
                learnLastRepetition = Date(),
                success = false
            )
        }

        word.learn = learn

        repository.editWord(word)
    }

    //Формула взята с https://habr.com/ru/articles/915202/
    private fun getRepeatTime(knowledgeLevel: Int) : Long{
        val date = Date().time + SECONDS_COEFFICIENT * 6 * 2.5.pow(knowledgeLevel.toDouble()).toLong()
        Log.d("TAG", "knowledgeLevel = ${knowledgeLevel} getRepeatTime = ${Date(date)}")
        return  date
    }

    companion object{
        const val SECONDS_COEFFICIENT = 5 * 1000
    }
}
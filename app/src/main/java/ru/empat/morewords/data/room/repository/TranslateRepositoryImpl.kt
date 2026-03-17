package ru.empat.morewords.data.room.repository

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import ru.empat.morewords.domain.entity.Language
import ru.empat.morewords.domain.repository.TranslateRepository
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor(): TranslateRepository {
    var options: TranslatorOptions? = null

    override fun saveSetting(
        wordLanguage: Language,
        translateLanguage: Language
    ) {
        options = TranslatorOptions.Builder()
            .setSourceLanguage(getLanguage(wordLanguage.id))
            .setTargetLanguage(getLanguage(translateLanguage.id))
            .build()

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()


        val modelTranslator = options?.let {
            Translation.getClient(it)
        }?: throw NullPointerException("Don't have options")

        modelTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Log.d("SAVE" , "Model save")
            }
            .addOnFailureListener { exception ->
                Log.d("SAVE" , "Model don't save")
            }
    }

    override fun translate(text: String, result: (String) -> Unit) {
        val modelTranslator = options?.let {
            Translation.getClient(it)
        }?: throw NullPointerException("Don't have options")

        modelTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                result.invoke(translatedText)
            }
            .addOnFailureListener { exception ->
                Log.e(TranslateRepositoryImpl::class.simpleName, exception.message, exception)
            }
    }

    override fun close() {

    }


    fun getLanguage(id: Long) : String{
        return when(id){
            1L -> TranslateLanguage.ENGLISH
            2L -> TranslateLanguage.RUSSIAN
            else -> {
                throw IllegalArgumentException("Language don't fined")
            }
        }
    }
}
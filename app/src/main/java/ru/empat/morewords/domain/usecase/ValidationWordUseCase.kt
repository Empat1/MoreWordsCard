package ru.empat.morewords.domain.usecase

import ru.empat.morewords.R
import javax.inject.Inject

class ValidationWordUseCase @Inject constructor() {
    fun getErrorOrNull(text: String, translate: String) : Int?{
        if(text.isNotEmpty()){
            return R.string.Word
        }

        return null
    }
}
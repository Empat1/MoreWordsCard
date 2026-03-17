package ru.empat.morewords.domain.entity

enum class Language(
    var id :Long,
    var title:String
) {
    EN(1, "Английский"),
    Russian(2, "Русский")
}
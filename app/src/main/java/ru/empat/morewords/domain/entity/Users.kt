package ru.empat.morewords.domain.entity

data class Users(
    var id:Int,
    var name: String,
    var language: String,
    var login: String,
    var password: String
){
    var foreignLanguages: List<Language>? = null
}

package com.example.langbuddy.model

data class User(
    val userId: Int,
    val firstName: String,
    val emailAddress:String,
    val spokenLanguages:List<Language>,
    val learningLanguages:List<Language>,
    val profilePictureUrl: String
) {
    override fun equals(other: Any?): Boolean {
        if (other is User) {
            if (other.userId == userId) {
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return userId
    }

    fun languagesFormatted():String{
        var formattedString=""
        for(language in spokenLanguages){
            formattedString=formattedString.plus(language.shortName+" ")
        }
        return formattedString
    }
}
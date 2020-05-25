package com.example.langbuddy

data class User(
    val id: Int,
    val name: String,
    val languages:String,
    val imageUrl: String
) {
    override fun equals(other: Any?): Boolean {
        if (other is User) {
            if (other.id == id) {
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        return id
    }
}
package com.example.langbuddy

import com.example.langbuddy.model.Language
import com.example.langbuddy.model.User
import org.junit.Assert
import org.junit.Test

class UserTest {

    @Test
    fun testEquals(){
        val user1= User(1,"Dominik","d.g@gmail.com", listOf(), listOf(),"temp.pnmg")
        val user2= User(1,"Martin","m.s@gmail.com", listOf(), listOf(),"temp.pnmg")
        Assert.assertTrue(user1==user2)
    }

    @Test
    fun testHashCode(){
        val user1= User(1,"Dominik","d.g@gmail.com", listOf(), listOf(),"temp.pnmg")
        Assert.assertEquals(1,user1.hashCode())
    }

    @Test
    fun testFormattedLanguages(){
        val user1= User(1,"","", listOf(Language(1,"GER","German"),Language(1,"ENG","English")), listOf(),"temp.pnmg")
        Assert.assertEquals("GER ENG ",user1.languagesFormatted())
    }
}
package com.example.langbuddy

import android.content.Context
import com.example.langbuddy.adapter.CustomAdapter
import com.example.langbuddy.model.User
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class CustomAdapterTest {
    val user1=User(1,"Dominik","d.g@gmail.com", listOf(), listOf(),"temp.pnmg")
    val user2=User(2,"Martin","m.s@gmail.com", listOf(), listOf(),"temp.pnmg")

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun getTheRightUser(){
        val adapter=createTestAdapter(listOf(user1,user2))
        Assert.assertEquals(user1,adapter.getItem(0))
        Assert.assertEquals(user2,adapter.getItem(1))
    }

    @Test
    fun getRightViewTypeCount(){
        val adapter=createTestAdapter(listOf(user1,user2))
        Assert.assertEquals(2,adapter.viewTypeCount)
    }

    @Test
    fun getRightCount(){
        val adapter=createTestAdapter(listOf(user1,user2))
        Assert.assertEquals(2,adapter.count)
    }

    fun createTestAdapter(users: List<User>): CustomAdapter {
        return CustomAdapter(
            Mockito.mock(Context::class.java), users)
    }
}
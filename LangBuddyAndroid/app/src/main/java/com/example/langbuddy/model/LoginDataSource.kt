package com.example.langbuddy.model

import com.example.langbuddy.model.LoggedInUser
import com.example.langbuddy.model.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.json.JSONObject
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    val client = HttpClient()
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {


            // TODO: handle loggedInUser authentication

            val jsonMessage = JSONObject()
            jsonMessage.put("email", username)
            jsonMessage.put("password", password)
            val response =
                client.post<String>("https://mcm-langbuddy.herokuapp.com/api/auth/login") {
                    contentType(ContentType.Application.Json)
                    body = jsonMessage.toString(1)
                }
            val jsonResponse = JSONObject(response)
            println(username)
            println(password)
            println(jsonResponse)
            if(jsonResponse["message"]=="Success"){
                val user = LoggedInUser(jsonResponse["userId"] as Int, username)
                return Result.Success(user)
            }else{
                return Result.Error(IOException("Error logging in", Throwable()))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}


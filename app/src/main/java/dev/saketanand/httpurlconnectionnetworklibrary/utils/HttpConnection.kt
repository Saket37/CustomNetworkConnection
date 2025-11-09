package dev.saketanand.httpurlconnectionnetworklibrary.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.saketanand.httpurlconnectionnetworklibrary.remote.model.GetPostResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HttpConnection {
    var httpURLConnection: HttpsURLConnection? = null
    val url = URL("https://jsonplaceholder.typicode.com/posts")

    suspend fun execute(): List<GetPostResponseItem> = withContext(Dispatchers.IO) {
        httpURLConnection = url.openConnection() as HttpsURLConnection

        val code = httpURLConnection?.responseCode
        if (code != 200) {
            println("Failure")
        }

        val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection?.getInputStream()))

        val jsonStringHolder: StringBuilder = StringBuilder()
        while (true) {
            val readLine = bufferedReader.readLine()
            if (readLine == null) {
                break
            } else {
                jsonStringHolder.append(readLine)
            }
        }
        val gson = Gson()
        val type = object : TypeToken<List<GetPostResponseItem>>() {}.type
        val userProfileResponse: List<GetPostResponseItem> =
            gson.fromJson(jsonStringHolder.toString(), type)
        return@withContext userProfileResponse
    }

}
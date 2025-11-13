package dev.saketanand.httpurlconnectionnetworklibrary.utils

import android.util.Log
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

suspend fun httpPostConnection(apiUrl: String, jsonBody: String): String =
    withContext(Dispatchers.IO) {
        var urlConnection: HttpURLConnection? = null
        val result: StringBuilder = StringBuilder()

        try {
            val url = URL(apiUrl)
            urlConnection = url.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "POST"
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8")
            urlConnection.doOutput = true // Enable writing data to the connection
            OutputStreamWriter(
                urlConnection.getOutputStream() as OutputStream,
                "UTF-8"
            ).use { writer ->
                writer.write(jsonBody)
                writer.flush()
            }
            val code = urlConnection.responseCode
            if (code in 200..299) {
                urlConnection.inputStream.bufferedReader().use { bufferedReader ->
                    bufferedReader.forEachLine { line ->
                        result.append(line)
                    }
                }
                Log.d("success", "Server returned response: $result")
            } else {
                val errorResult = StringBuilder()
                urlConnection.errorStream?.bufferedReader()?.use { reader ->
                    reader.forEachLine { errorResult.append(it) }
                }
                Log.d("Error", "Server returned error code: $code. Body: $errorResult")
                throw IOException("Server returned error code: $code")
            }

        } catch (e: Exception) {
            throw e
        } finally {
            urlConnection?.disconnect()
        }
        return@withContext result.toString()
    }

fun createPostData(): String {
    val postData = JsonObject()
    postData.addProperty("title", "Test title")
    postData.addProperty("body", "Test body")
    postData.addProperty("userId", 1)
    return postData.toString()
}
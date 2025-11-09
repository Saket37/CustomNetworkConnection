package dev.saketanand.httpurlconnectionnetworklibrary.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

suspend fun httpGetConnection(apiUrl: String): String = withContext(Dispatchers.IO) {
    var urlConnection: HttpURLConnection? = null
    val result: StringBuilder = StringBuilder()
    try {
        val url = URL(apiUrl)
        urlConnection = url.openConnection() as HttpsURLConnection
        // Default is GET, but setting it explicitly is good practice
        urlConnection.requestMethod = "GET"

        val code = urlConnection.responseCode
        if (code != 200) {
            throw Exception("Invalid response from server: $code")
        }
        /**
         * Read the response from the server
         * smart kotlin ext fun
         * By default, it uses Charsets.UTF_8
         */
        urlConnection.inputStream.bufferedReader().use { bufferedReader ->
            bufferedReader.forEachLine { line ->
                result.append(line)
            }
        }
        if (result.isEmpty()) {
            throw Exception("Invalid or empty response from server: $code")
        }


    } catch (e: Exception) {
        throw e
    } finally {
        urlConnection?.disconnect()
    }
    return@withContext result.toString()
}
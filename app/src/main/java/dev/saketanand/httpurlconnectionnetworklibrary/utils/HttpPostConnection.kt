package dev.saketanand.httpurlconnectionnetworklibrary.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

suspend fun httpPostConnection(apiUrl: String, jsonBody: String) = withContext(Dispatchers.IO) {
    var urlConnection: HttpURLConnection? = null
    val result: StringBuilder = StringBuilder()

    try {
        val url = URL(apiUrl)
        urlConnection = url.openConnection() as HttpsURLConnection
        urlConnection.requestMethod = "POST"
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8")

        urlConnection.getOutputStream().use {

        }
    } catch (e: Exception) {

    } finally {
        urlConnection?.disconnect()
    }
}
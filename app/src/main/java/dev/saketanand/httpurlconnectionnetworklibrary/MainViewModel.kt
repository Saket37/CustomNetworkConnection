package dev.saketanand.httpurlconnectionnetworklibrary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dev.saketanand.httpurlconnectionnetworklibrary.remote.model.GetPostResponseItem
import dev.saketanand.httpurlconnectionnetworklibrary.utils.HttpConnection
import dev.saketanand.httpurlconnectionnetworklibrary.utils.httpGetConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainUiState(
    val data: List<GetPostResponseItem> = emptyList()
)

class MainViewModel : ViewModel() {

    var httpConnection: HttpConnection? = null
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        httpConnection = HttpConnection()
        viewModelScope.launch {
            val data = httpGetConnection("https://jsonplaceholder.typicode.com/posts")
            val jsonObject = Gson().fromJson(data, Array<GetPostResponseItem>::class.java).toList()
                _uiState.update {
                    it.copy(
                        data = jsonObject
                    )
                }
        }


    }
}
package dev.saketanand.httpurlconnectionnetworklibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.saketanand.httpurlconnectionnetworklibrary.remote.model.GetPostResponseItem
import dev.saketanand.httpurlconnectionnetworklibrary.utils.HttpConnection
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
        if (httpConnection != null) {
            viewModelScope.launch {
                val data = httpConnection?.execute()
                _uiState.update {
                    it.copy(
                        data = data.orEmpty()
                    )
                }
            }
        }

    }
}
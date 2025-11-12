package dev.saketanand.httpurlconnectionnetworklibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.saketanand.httpurlconnectionnetworklibrary.ui.component.Item
import dev.saketanand.httpurlconnectionnetworklibrary.ui.theme.HttpURLConnectionNetworkLibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HttpURLConnectionNetworkLibraryTheme {
                val viewModel = viewModel<MainViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val state = rememberLazyGridState()
                val isExpanded by remember { derivedStateOf { state.firstVisibleItemIndex == 0 } }

                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        contentColor = Color.Black,
                        containerColor = Color.Cyan
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
//                            if (isExpanded) {
//                                Text(text = "Add", fontSize = 12.sp, fontWeight = FontWeight.Normal)
//                            }
                        }
                    }
                }) { innerPadding ->
                    val pad =
                        if (state.firstVisibleItemIndex > 1) PaddingValues(0.dp) else innerPadding
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = pad)
                    ) {
                        ItemList(uiState = uiState, listState = state)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemList(modifier: Modifier = Modifier, uiState: MainUiState, listState: LazyGridState) {
    LazyVerticalGrid(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {

        itemsIndexed(items = uiState.data, key = { _, item -> item.id!! }) { _, item ->
            Item(
                title = item.title.toString(),
                dec = item.body.toString(),
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HttpURLConnectionNetworkLibraryTheme {
        Greeting("Android")
    }
}
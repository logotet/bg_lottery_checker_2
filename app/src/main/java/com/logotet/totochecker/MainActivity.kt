package com.logotet.totochecker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import com.logotet.totochecker.data.remote.RemoteWinningNumbersDataSource
import com.logotet.totochecker.presentation.ui.main.MainScreen
import com.logotet.totochecker.ui.theme.TotoCheckerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var remoteWinningNumbersDataSource: RemoteWinningNumbersDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.coroutineScope.launch {
            remoteWinningNumbersDataSource.getWinningNumbers49().forEach{
                Log.e("blia", "6 49 $it", )
            }
            remoteWinningNumbersDataSource.getWinningNumbers42().forEach{
                Log.d("blia", "6 42 $it", )
            }
            remoteWinningNumbersDataSource.getWinningNumbers35FirstPick().forEach{
                Log.e("blia", "5 35 (1) $it", )
            }
            remoteWinningNumbersDataSource.getWinningNumbers35SecondPick().forEach{
                Log.d("blia", "5 35 (2) $it", )
            }
        }

        setContent {
            TotoCheckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
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
    TotoCheckerTheme {
        Greeting("Android")
    }
}
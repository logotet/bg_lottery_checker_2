package com.logotet.totochecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.logotet.totochecker.presentation.navigation.Route
import com.logotet.totochecker.presentation.ui.check.NumberCheckerScreen
import com.logotet.totochecker.presentation.ui.composables.BottomAppBar
import com.logotet.totochecker.presentation.ui.main.MainScreen
import com.logotet.totochecker.ui.theme.TotoCheckerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TotoCheckerTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var appBarTitle by remember {
                        mutableStateOf(Route.Home.label)
                    }

                    Scaffold(
                        topBar = {
                            TopAppBar(title = { Text(text = appBarTitle) })
                        },
                        bottomBar = {
                            BottomAppBar(navController = navController)
                        }
                    ) { paddingValues ->
                        NavHost(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                            startDestination = Route.Home.route
                        ) {
                            composable(Route.Home.route) {
                                appBarTitle = Route.Home.label
                                MainScreen(
                                    viewModel = hiltViewModel()
                                )
                            }

                            composable(Route.Check.route) {
                                appBarTitle = Route.Check.label
                                NumberCheckerScreen(
                                    viewModel = hiltViewModel()
                                )
                            }

                            composable(Route.MyNumbers.route) {
                                appBarTitle = Route.MyNumbers.label
                                MainScreen(
                                    viewModel = hiltViewModel(),
                                    backgroundColor = Color.Yellow
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
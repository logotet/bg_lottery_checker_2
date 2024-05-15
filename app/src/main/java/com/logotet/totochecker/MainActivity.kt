package com.logotet.totochecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.logotet.totochecker.presentation.navigation.Route
import com.logotet.totochecker.presentation.ui.composables.BottomAppBar
import com.logotet.totochecker.presentation.ui.main.MainScreen
import com.logotet.totochecker.ui.theme.TotoCheckerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TotoCheckerTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
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
                                MainScreen()
                            }

                            composable(Route.Check.route) {
                                MainScreen(backgroundColor = Color.Gray)
                            }

                            composable(Route.MyNumbers.route) {
                                MainScreen(backgroundColor = Color.Yellow)
                            }
                        }
                    }

                }
            }
        }
    }
}
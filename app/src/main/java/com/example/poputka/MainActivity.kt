package com.example.poputka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.poputka.presentation.navigation.BottomNavBar
import com.example.poputka.presentation.navigation.graphs.AppNavGraph
import com.example.poputka.presentation.navigation.nav_items.Screen
import com.example.poputka.presentation.navigation.util.currentRouteAsState
import com.example.poputka.presentation.navigation.util.currentScreenAsState
import com.example.poputka.ui.theme.PoputkaTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        Firebase.initialize(applicationContext)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentSelectedScreen by navController.currentScreenAsState()
            val currentRoute by navController.currentRouteAsState()

            val bottomNavRoutes = listOf(
                Screen.Home.name,
                Screen.Search.name
            )

            PoputkaTheme {
                Scaffold(bottomBar = {
                    if (currentRoute == null || bottomNavRoutes.contains(currentRoute)) {
                        BottomNavBar(
                            navController = navController,
                            currentSelectedScreen = currentSelectedScreen
                        )
                    }
                }) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        AppNavGraph(navController = navController)
                    }
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
    PoputkaTheme {
        Greeting("Android")
    }
}
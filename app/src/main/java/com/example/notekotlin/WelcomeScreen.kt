package com.example.notekotlin
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
     Column (horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center ){
         Text(text = "Welcome to the Note App")
         Spacer(modifier = Modifier.height(16.dp))
         Button(onClick = {
             navController.navigate(Routes.HomeScreen)
         }) {
             Text(text = "Let's start")

         }
     }
    }
}
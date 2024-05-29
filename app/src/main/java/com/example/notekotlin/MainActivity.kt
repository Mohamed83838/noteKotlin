package com.example.notekotlin

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notekotlin.sharedpref.sharedpref
import com.example.notekotlin.ui.theme.NotekotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
val logged : sharedpref =sharedpref()
        val islogged=logged.readData(this,"logged")
        var start :String
     if(islogged==null){
         start=Routes.WelcomeScreen
         logged.saveData(this,"logged","true")
     }else{
         start=Routes.HomeScreen
     }
        setContent {
            NotekotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = start) {
                        composable(Routes.WelcomeScreen) { WelcomeScreen( navController=navController )}
                        composable(Routes.HomeScreen) { HomeScreen( navController=navController,this@MainActivity ) }
                        composable(Routes.AddNoteScreen) { AddNote( navController=navController ,this@MainActivity) }

                    }

                }
            }
        }
    }
}


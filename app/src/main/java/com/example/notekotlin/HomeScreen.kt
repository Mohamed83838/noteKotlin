package com.example.notekotlin

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.notekotlin.database.Note
import com.example.notekotlin.database.NoteManagerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController :NavController,context: Context) {
    val notes = remember { mutableStateOf<List<Note>>(emptyList()) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
        ) {
Scaffold(
    topBar = {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Note App")
            },

        )

    },
    floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Routes.AddNoteScreen)
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
    ) { paddingValues ->
    val db = NoteManagerRepo(context)


    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            notes.value = db.getAllEntries()
        }
    }

    LazyColumn(

        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp, vertical = 20.dp)

    ) {
        items(notes.value) { note: Note ->
            Home_Note(note, context = context){
                notes.value = db.getAllEntries()
                VisualTools.showToast(context = context,"Deleted successfully")
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
    }
    }
}

@Composable
fun Home_Note(note:Note,context: Context,onDelete: () -> Unit) {
    var openDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
            title = {
                Text(text = "Delete")
            },
            text = {
                Text(
                    "this action cant be undone are you sure"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {

                        val db=NoteManagerRepo(context)

                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                db.delete(note.id)

                            }
                            onDelete()
                        }


                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
        .padding(10.dp)
        .clickable {
            openDialog.value = true;
        }

    ){

       Row (horizontalArrangement = Arrangement.Absolute.SpaceBetween , modifier = Modifier.fillMaxWidth()){
          Row {
              Box(modifier = Modifier
                  .height(50.dp)
                  .width(50.dp)
                  .border(1.dp, Color.Black, RoundedCornerShape(100))
              ){
                  val imagePath = note.image // Replace with your actual path
                  Image(
                      painter = rememberImagePainter(File(imagePath)),
                      contentDescription = "Image", // Provide a meaningful description
                      modifier = Modifier.fillMaxSize()
                          .clip(RoundedCornerShape(100))
                  // Ensure image fills the box
                  )
              }
              Spacer(modifier = Modifier.width(15.dp))
              Column (){

                  Text(text = note.title)
                  Text(text = note.content , modifier = Modifier.width(200.dp))


              }
          }
           var checked by remember { mutableStateOf(true) }

           Checkbox(
               checked = checked,
               onCheckedChange = { checked = it }
           )

       }
    }

}


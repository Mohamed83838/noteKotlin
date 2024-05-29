package com.example.notekotlin
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.notekotlin.database.NoteManagerRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNote(navController: NavController,context: Context) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    Scaffold ( floatingActionButton = {
        FloatingActionButton(onClick = {
            val db = NoteManagerRepo(context)
            db.insert(title = title, content = description, image = selectedImageUri.let { getPathFromUri(context, it) })
            navController.navigate(Routes.HomeScreen)
        }) {
            Icon(Icons.Default.Done, contentDescription = "Add")
        }
    },topBar = {
        TopAppBar(
            navigationIcon =  {Icon(Icons.Default.ArrowBack, contentDescription = "Add", modifier = Modifier
                .padding(10.dp)
                .clickable { navController.navigate(Routes.HomeScreen) })
                              },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Note App")
            },

            )

    }){paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp, vertical = 20.dp)) {
            val rainbowColors = listOf(
                Color.Blue,
                Color.Black,

            )


            val brush = remember {
                Brush.linearGradient(
                    colors = rainbowColors
                )
            }
            Text(text = "Enter the Title :" , style = TextStyle( fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),
                maxLines = 1,
                label = { Text("Enter Title") },
                value = title, onValueChange = { title = it }, textStyle = TextStyle(brush = brush)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Enter the Description :" , style = TextStyle( fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),
                maxLines = 10,
                label = { Text("Enter Title") },
                value = description, onValueChange = { description = it }, textStyle = TextStyle(brush = brush)
            )
            Spacer(modifier = Modifier.height(15.dp))
           if(selectedImageUri==null){
               Box(contentAlignment = Alignment.Center,
                   modifier = Modifier
                       .height(200.dp)
                       .fillMaxWidth()
                       .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                       .clip(
                           RoundedCornerShape(12.dp)

                       ),
               ){



                   Box(
                       contentAlignment = Alignment.Center,
                       modifier = Modifier
                           .height(50.dp)
                           .width(50.dp)
                           .border(
                               width = 1.dp,
                               color = MaterialTheme.colorScheme.primary,
                               shape = RoundedCornerShape(100)
                           )

                   ){
                       Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier
                           .padding(10.dp)
                           .clickable {
                               singlePhotoPickerLauncher.launch(
                                   PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                               )
                           })

                   }


               }




           }else{

               Box(contentAlignment = Alignment.Center,
                   modifier = Modifier
                       .height(200.dp)
                       .fillMaxWidth()
                       .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                       .clip(
                           RoundedCornerShape(12.dp)

                       ),
               ){


                   AsyncImage(
                       model = selectedImageUri,
                       contentDescription = null,
                       modifier = Modifier.fillMaxWidth(),
                       contentScale = ContentScale.Crop
                   )
                   Box(
                       contentAlignment = Alignment.Center,
                       modifier = Modifier
                           .height(50.dp)
                           .width(50.dp)
                           .border(
                               width = 1.dp,
                               color = Color.Black,
                               shape = RoundedCornerShape(100)
                           )

                   ){
                       Icon(Icons.Default.Edit, contentDescription = "Add", modifier = Modifier
                           .padding(10.dp)
                           .clickable {
                               singlePhotoPickerLauncher.launch(
                                   PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                               )
                           })

                   }


               }




           }
        }

    }
}
fun getPathFromUri(context: Context, uri: Uri?): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = uri?.let { context.contentResolver.query(it, projection, null, null, null) }
    cursor?.use {
        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        it.moveToFirst()
        return it.getString(columnIndex)
    }
    return "null"
}

package com.example.takenotes.ui.screens.addupdatenote

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.takenotes.R
import com.example.takenotes.core.ApplicationClass
import com.example.takenotes.data.Notes
import com.example.takenotes.ui.screens.home.VLRfontfamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

data class AddUpdateNotesHere(
    val notes: Notes? = null
) : Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            EditNotes(notes = notes)
        }
    }
}

@Composable
fun EditNotes(
    modifier: Modifier = Modifier, notes: Notes?
) {
    //Parent (EditNotes) owns pickedImgUri (the selected image URI).
    // this updates time to time
    var pickedImgUri by remember { mutableStateOf<Uri?>(null) }
    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            pickedImgUri = uri
        }
    }
    val navigator = LocalNavigator.currentOrThrow
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var title = remember {
        mutableStateOf(notes?.tittle ?: "")
    }
    val body = remember {
        mutableStateOf(notes?.description ?: "")
    }
    val dao = ApplicationClass.getApp(context).dao
    Box(modifier = Modifier.padding(top = 46.dp)) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(modifier = Modifier.size(38.dp), onClick = {
                    navigator.pop()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.backarrow),
                        contentDescription = "Menu Icon",
                        tint = Color.Unspecified
                    )

                }
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                Text(
                    "Write Notes!",
                    color = Color(0xFF92B0F8),
                    fontFamily = VLRfontfamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 28.dp)

                )
            }

            Column {

                Spacer(modifier = Modifier.height(24.dp))


                OutlinedTextField(value = title.value, onValueChange = { newValue: String ->
                    Log.e("112233", "onValueChanged $newValue")
                    title.value = newValue
                }, label = {
                    Text(
                        "Note Title",
                        fontFamily = VLRfontfamily,
                        color = Color(0xFF8299CF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }, placeholder = { Text(text = "Untitled") }, modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(value = body.value,
                    onValueChange = { newValue: String ->
                        body.value = newValue
                    },
                    label = {
                        Text(

                            "Note Body",
                            fontFamily = VLRfontfamily,
                            color = Color(0xFF8299CF),
                            fontSize = 16.sp,
                        )
                    },
                    placeholder = { Text(text = "Write Description ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)

                )
            }
// conversion of uri to bitmap
            if(pickedImgUri == null) {
                notes?.imageUrl?.let {
                    Image(bitmap = byteArrayToBitmap(it).asImageBitmap(), contentDescription = "", modifier = Modifier.size(90.dp))
                }
            }
            ImagePicker(
                imageUri = pickedImgUri,
                onImagePick = {

                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
//         FloatingActionButton
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                // Condition
                FloatingActionButton(containerColor = Color(0xFF92B0F8),
                    contentColor = Color.White,
                    modifier = Modifier
                        .padding(12.dp)
                        .width(100.dp),
                    onClick = {
                        if (title.value.isNotEmpty() && body.value.isNotEmpty()) {

                            GlobalScope.launch(Dispatchers.IO) {

                                if (notes == null) {
                                    dao.insertNote(
                                        Notes(
                                            tittle = title.value,
                                            description = body.value,
                                            imageUrl = pickedImgUri?.let { uriToByteArray(it, context) }
                                            )
                                    )
                                } else {
                                    var updatedNote = Notes(
                                        id = notes.id,
                                        tittle = title.value,
                                        description = body.value,
                                        updatedAt = System.currentTimeMillis(),
                                        imageUrl = notes.imageUrl
                                    )
                                    if(pickedImgUri != null){
                                        uriToByteArray(pickedImgUri!!, context)?.let {byteArray ->
                                            updatedNote = updatedNote.copy(
                                                imageUrl = byteArray
                                            )
                                        }
                                    }
                                    dao.updateNote(updatedNote)
                                }
                            }
                            navigator.pop()
                        } else {
                            val text = "Please fill all the fields"
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                    if (notes == null) {
                        Text(text = "Save", fontSize = 16.sp)
                    } else {
                        Text(text = "Update", fontSize = 16.sp)
                    }


                }
            }

        }
    }
}


fun uriToByteArray(uri: Uri, context: Context): ByteArray? {
    //context Needed to access contentResolver to get the image data.
    // contentResolver.openInputStream(uri) opens a stream to read the image file from storage.
    return try {8
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        //converts the image data into a Bitmap.
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        //The Bitmap is compressed as PNG and written to outputStream.
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.toByteArray()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

@Composable
fun ImagePicker(
    //Child (ImagePicker) cannot change pickedImgUri directly
    // hoisting the image uri
    imageUri: Uri? = null,     // Receives the current image state.
  //  Child calls onImagePick() to ask the parent to pick an image.

onImagePick: () -> Unit,   // Receives a callback to launch the gallery.
) {
// img picking code
    Column {
        // UI
        imageUri?.let { uri ->
            AsyncImage(
                model = uri, contentDescription = "Selected Image"
            )
        }
        Button(onClick = onImagePick) {
            Text("Pick an Image")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EditNotes(notes = null)
}
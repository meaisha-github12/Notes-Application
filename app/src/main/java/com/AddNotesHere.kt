package com

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.takenotes.ApplicationClass
import com.example.takenotes.HomeView
import com.example.takenotes.Notes
import com.example.takenotes.R
import com.example.takenotes.VLRfontfamily
import com.example.takenotes.ui.theme.TakeNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNotesHere : Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(modifier = Modifier.fillMaxSize(),
        ) {

            EditNotes()
        }

    }
}

suspend fun somecode(){}

@Composable
fun EditNotes(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var title = remember {
        mutableStateOf("")
    }
    val body = remember {
        mutableStateOf("")
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

            Column (

            ){

                Spacer(modifier = Modifier.height(24.dp))


                OutlinedTextField(
                    value = title.value,
                    onValueChange = { newValue: String ->
                        Log.e("112233", "onValueChanged $newValue")
                        title.value = newValue
                    },
                    label = { Text("Note Title",
                        fontFamily = VLRfontfamily,
                        color = Color(0xFF8299CF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                        ) },
                    placeholder = { Text(text = "Untitled") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = body.value,
                    onValueChange = { newValue: String ->
                        body.value = newValue
                    },
                    label = { Text("Note Body",
                        fontFamily = VLRfontfamily,
                        color = Color(0xFF8299CF),
                        fontSize = 16.sp,
                        ) },
                    placeholder = { Text(text = "Write Description ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)

                )
            }

            Spacer(modifier = Modifier.height(24.dp))
//         FloatingActionButton
            Row( modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                // Condition

                    FloatingActionButton(
                        containerColor = Color(0xFF92B0F8),
                        contentColor = Color.White,
                        modifier = Modifier
                            .padding(12.dp)
                            .width(100.dp),
                        onClick = {
                            if (title.value.isNotEmpty() && body.value.isNotEmpty()) {
//                                Thread {
//                                    dao.insertNote(
//                                        Notes(
//                                            tittle = title.value,
//                                            description = body.value
//                                        )
//                                    )
//                                }.start()
                                GlobalScope.launch(Dispatchers.IO)
                                {
                                    dao.insertNote(
                                        Notes(
                                            tittle = title.value,
                                               description = body.value
                                        )
                                    )
                                }
                            navigator.pop()}
                            else{
                                val text = "Please fill all the fields"
                                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                            }
                        }) {
                        Text(text = "Save", fontSize = 16.sp)
                    }
                }

        }
        }
    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TakeNotesTheme {
        EditNotes()
    }
}
package com.example.takenotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.AddNotesHere
import com.example.takenotes.ui.theme.TakeNotesTheme
import com.google.ai.client.generativeai.type.content
import com.notesList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TakeNotesTheme {
                Navigator(HomeScreen())

            }
        }
    }
}

class HomeScreen() : Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(modifier = Modifier.fillMaxSize() ) {
            HomeView()


        }

    }
}

val VLRfontfamily = FontFamily(Font(R.font.varelaroundregular))

@Composable
fun HomeView(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    Box(modifier = Modifier.padding(top = 46.dp)) {
        Column(modifier.padding(12.dp)) {
            // Top bar with menu icon and profile picture
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(modifier = Modifier.size(48.dp), onClick = {
                    // Handle the click event here
                }) {
                    Icon(
                        painter = painterResource(R.drawable.menuicon),
                        contentDescription = "Menu Icon",
                        tint = Color.Unspecified
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.girl),
                    contentDescription = "profile",
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .size(48.dp)
                )
            }
            Spacer(
                modifier = Modifier.padding(12.dp)

            )
            // FAVOURITES text Only
            Text(
                "Favourites",
                color = Color(0xFF92B0F8),
                fontFamily = VLRfontfamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 28.dp)
            )
            Spacer(modifier.padding(12.dp))
            // LazyVerticalStaggeredGrid Only
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(180.dp),
                modifier = Modifier
                    // weight here
                    .weight(1f),
                content = {
                    items(notesList) { note ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(100.dp)
//                                .height((65 + (index % 5) * 30).dp)
                                .background(
                                    Color(0xFF7793D6), shape = RoundedCornerShape(12.dp)

                                )

                        ) {
                            Text(
                                text = note.title,
                                modifier = Modifier.padding(8.dp),
                                fontSize = 16.sp,
                                fontFamily = VLRfontfamily,
                                color = Color.White
                            )
                            Text(
                                text = note.body,
                                modifier = Modifier.padding(8.dp),
                                fontSize = 16.sp,
                                fontFamily = VLRfontfamily,
                                color = Color.White
                            )
                        }

                    }
                })
            // Floating Button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    containerColor = Color(0xFF92B0F8),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .padding(12.dp),
                    onClick = { /*TODO*/
                        navigator.push(AddNotesHere())
                    }) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_add_24),
                            contentDescription = "Add Vector Asset",
                            modifier = Modifier
                                .padding(4.dp)
                                .size(26.dp)



                        )
                      //  Spacer(modifier = Modifier.width(2.dp)) // Space between icon and te
                        Text(text = "Create",
                            fontSize = 16.sp,
                            fontFamily = VLRfontfamily,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier =Modifier.padding(6.dp)
                        )
                    }
                }
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TakeNotesTheme {
        HomeView()
    }
}
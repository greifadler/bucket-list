package at.styrialabs.bucketlist

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import at.styrialabs.bucketlist.domain.BucketListElement
import at.styrialabs.bucketlist.ui.theme.BucketlistTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: BucketListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BucketlistTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val list: List<BucketListElement> by viewModel.bucketLists.observeAsState(initial = emptyList())
        MainScreen(list = list)
    }

    @Composable
    fun MainScreen(list : List<BucketListElement>)
    {
        Scaffold(
            topBar = {
            Headline(Modifier)
            },
            bottomBar = {
                NewElement(modifier = Modifier)
            }) { innerPadding ->
            LazyColumn (Modifier.padding(innerPadding)) {
                items(list) { it ->
                    Element(element = it)
                }
            }
        }
    }

    @Composable
    fun Headline(modifier: Modifier) {
        BoxWithConstraints(modifier.height(180.dp)) {
            Icon(
                painter = painterResource( R.drawable.ic_paint_bucket),
                contentDescription = "goal",
                modifier = Modifier
                    .width(110.dp)
                    .height(110.dp)
                    .align(Alignment.TopEnd)
                    .graphicsLayer {
                        rotationY = 180f
                    }
                    .padding(12.dp)
                )
            Text(
                text = "Bucket List",
                fontSize = 48.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
            Divider(
                thickness = 4.dp,
                color = colorResource(id = R.color.black),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }

    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Element(element: BucketListElement) {
        val done = element.doneAt != null
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                if(!done) viewModel.setToDone(element) else viewModel.delete(element) },
            elevation = 10.dp,
            backgroundColor = colorResource(id = if (done) R.color.green else R.color.blue)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)) {
                    Icon(
                        painterResource(id = if (done) R.drawable.ic_success else R.drawable.ic_goal),
                        "goal",
                        Modifier
                            .width(36.dp)
                            .height(36.dp)
                    )
                    Text(
                        text = element.text,
                        modifier = Modifier.padding(10.dp, 0.dp),
                        textDecoration = if (done) TextDecoration.LineThrough else TextDecoration.None
                    )
            }
        }
    }

    @Composable
    fun NewElement(modifier: Modifier) {
        Card(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { } ,
            shape = RoundedCornerShape(50),
            elevation = 10.dp,
            backgroundColor = colorResource(id = R.color.white)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp, 0.dp)) {
                Icon(
                    painterResource(id = R.drawable.ic_trophy),
                    "goal",
                    Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
                var textState by remember { mutableStateOf(String()) }

                TextField(
                    value = textState,
                    label = { Text(text = stringResource(id = R.string.new_bucketlist_entry_text)) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Green,

                        ),
                    modifier = Modifier.weight(1f),
                    onValueChange = {
                    textState = it
                })
                IconButton(onClick = {
                    if(textState.isNotEmpty())
                    {
                        viewModel.addBucketList(textState)
                        textState = ""
                    }
                },
                    Modifier.padding(10.dp, 0.dp)) {
                    Icon(
                        painterResource(id = R.drawable.ic_send),
                        "send",
                        Modifier
                            .width(19.dp)
                            .height(19.dp)
                    )
                }

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreviewDone() {
        BucketlistTheme {
            Element(BucketListElement(0,"Einen Berg besteigen", LocalDateTime.now(), LocalDateTime.now() ))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreviewTodo() {
        BucketlistTheme {
            Element(BucketListElement(0,"Den Mount Everest besteigen", LocalDateTime.now(), null ))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun HeadlinePreview() {
        BucketlistTheme {
            Headline(Modifier)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun NewElementPreview() {
        BucketlistTheme {
            MainScreen(emptyList())
        }
    }

}


package com.example.testing

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.webkit.WebView
import android.webkit.WebViewClient
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testing.viewmodel.MainActivityViewModel
import kotlinx.coroutines.delay
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(color = Color( 0x00FF_0000), modifier = Modifier.fillMaxSize()) {

                    Navigation()

            }
        }

    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)

        }
        composable("web_screen") {
                WebScreen()
        }
        composable("text_screen") {
            TextScreen(viewModel())
        }

    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)

                }
            )

        )
        delay(3000L)
        navController.popBackStack()
        val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val navdest = if (hourOfDay in 9 until 12) {
        "web_screen"
    } else {
        "text_screen"
    }
        navController.navigate(navdest)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = "Logo",
        modifier = Modifier.scale(scale.value)

        )
    }
}

@Composable
fun WebScreen() {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT

            )
            webViewClient = WebViewClient()
            loadUrl("https://trafic.monster/")
        }
    }, update = {
        it.loadUrl("https://trafic.monster/")
    })

}

@Composable
fun TextScreen(viewModel:MainActivityViewModel) {
    val helloText by viewModel.word.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageResource(id = R.drawable.nature)

            val openDialog = remember { mutableStateOf(false) }

            Button(
                onClick = {viewModel.setHelloText("Hello")},
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                )

            ) {
                Text("Press Me")
            }
            if(helloText.isNotEmpty()) { Text(helloText) }


            Row {
                ImageResource(id = R.drawable.nature)
                ImageResource(id = R.drawable.nature)
            }
        }
    }
}


@Composable
fun ImageResource(id: Int) {
    val painter: Painter = painterResource(id)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
    )
}




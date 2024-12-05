package com.nsicyber.wciwapp.presentation.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsicyber.wciwapp.R
import com.nsicyber.wciwapp.presentation.components.BaseView
import com.nsicyber.wciwapp.primaryColor
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(onSplashFinished: () -> Unit = {}) {
    val context = LocalContext.current
    val text = stringResource(R.string.what_can_i_watch)
    val words = text.split(" ")
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    BaseView(content = {
        Box(
            modifier = Modifier
                .background(primaryColor)
                .fillMaxSize()
                .padding(vertical = 32.dp, horizontal = 16.dp)
        ) {
            Column(modifier = Modifier .fillMaxWidth()
                .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
               ) {
                words.forEachIndexed { index, word ->
                    val fontSize = remember { (35..60).random().sp }
                    val padding =
                        if (index % 2 == 0) PaddingValues(end = 32.dp) else PaddingValues(start = 32.dp)
                    Text(
                        text = word,
                        fontSize = fontSize,
                        color = Color.White,
                        style = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .padding(padding)
                    )
                }
            }
            Text(
                style = TextStyle(textAlign = TextAlign.Center),
                text = stringResource(R.string.this_product_uses_the_tmdb_api_but_is_not_endorsed_or_certified_by_tmdb),
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    })


}

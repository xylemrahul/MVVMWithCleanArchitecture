package com.photon.findmyip

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.photon.findmyip.models.Result
import com.photon.findmyip.ui.theme.FindMyIPTheme
import com.photon.findmyip.viewmodel.IPViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FindMyIPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowUi()
                }
            }
        }
    }
}


@Composable
fun ShowUi(){

    val ipViewModel : IPViewModel = hiltViewModel()
    val result = ipViewModel.ipList.observeAsState()
    val loading = ipViewModel.loading.value

    when(result.value?.status){
        Result.Status.SUCCESS -> {
            result.value?.data?.let {
                CircularIndeterminateProgressBar(false)
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(content = {
                        items(1) {
                            CardItem(data = result.value?.data.toString())
                        }
                    })
                }
            }
        }
        Result.Status.ERROR -> {
            result.value?.message?.let {
                CircularIndeterminateProgressBar(false)
            }
            ProgressBar.GONE
        }

        Result.Status.LOADING -> {
            CircularIndeterminateProgressBar(true)
        }

        else -> {}
    }

}


@Composable
fun CardItem(data: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
        content = {
            Text(
                text = data,
                modifier = Modifier.padding(16.dp)
            )
        }
    )
}

@Composable
fun CircularIndeterminateProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FindMyIPTheme {
//        Greeting("Android")
    }
}
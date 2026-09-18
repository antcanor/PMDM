package com.example.pruebaaplicacion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pruebaaplicacion.ui.theme.PruebaAplicacionTheme

class MainActivity : ComponentActivity() {

    //TAG para el log de error
    private val TAG = "EspejoGaladriel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Se añade el mensaje de error
        Log.e(TAG, "Un Anillo para gobernarlos a todos")
        enableEdgeToEdge()
        setContent {
            PruebaAplicacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Cyan){
        Text(
            text = "Hola mi nombre es $name!",
            modifier = modifier.padding(all = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PruebaAplicacionTheme {
        Greeting("Android")
    }
}
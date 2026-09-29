package com.example.laboratorio2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val TAG = "PortalDeRivendell"

    //Se crea la variable contador, que es la que se va a usar para cambiar el texto del contador
    private var contador = 0

    //Se crea la variable que almacena el nuevo texto que va a aparecer al pulsar el botón
    private var textoNUevo = "¡La Comunidad del Anillo ha partido hacia Mordor!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            Log.d(TAG, "onCreate(): El Anillo despierta en la Comarca. La aventura comienza en Rivendell.")

            //Se recuperan el botón, texto, y contador
            val boton = findViewById<Button>(R.id.botonComunidad)
            val textoContador = findViewById<TextView>(R.id.contador)
            val textoInicio = findViewById<TextView>(R.id.textoInicio)

            //Al pulsar el botón se va a sumar 1 al contador que hemos creado
            //Se cambia el texto del contador, que en el xml se ha inicializado a 0
            //Se cambiar el texto de inicio al nuevo menaje
            boton.setOnClickListener {
                contador++
                textoContador.text = "Miembros reunidos: $contador"
                textoInicio.text = textoNUevo
            }





            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(): La Comunidad cruza las minas de Moria. El peligro es visible.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(): Llegada a Lothlórien. El portador del Anillo toma el control total de la marcha.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(): La Comunidad se rompe en Amon Hen. El camino se vuelve borroso y pausado.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(): Sam y Frodo entran en las sombras de Mordor. La app queda oculta a los ojos del mundo.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(): ¡El Anillo es arrojado al Monte del Destino! La misión ha terminado y la memoria se libera.")
    }
}
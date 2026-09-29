package com.example.laboratorio2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreadorPersonajeActivity : AppCompatActivity() {

    private val TAG = "LaForjaDelHéroe"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_creador_guerrero)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Vinculamos las variables con los componentes visuales del XML
        val Nombre = findViewById<EditText>(R.id.Nombre)
        Nombre.requestFocus()
        Nombre.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                // Validar si el campo se quedó vacío al abandonarlo
                if (Nombre.text.isEmpty()) Nombre.error = "¡Tu héroe necesita un nombre!"
            }
        }
        val spinnerRaza = findViewById<Spinner>(R.id.spinnerRaza)

        spinnerRaza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val razaSeleccionada = parent?.getItemAtPosition(position).toString()
                Log.d("Personaje", "Raza seleccionada: $razaSeleccionada")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val Faccion = findViewById<RadioGroup>(R.id.Faccion)
        Faccion.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbComunidad -> Toast.makeText(this, "¡Fiel a la Comunidad del Anillo!", Toast.LENGTH_SHORT).show()
                R.id.rbMordor -> Toast.makeText(this, "¡Fiel a las Huestes de Mordor!", Toast.LENGTH_SHORT).show()
            }
        }

        val Sigilo = findViewById<CheckBox>(R.id.Sigilo)
        Sigilo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) Log.d("Habilidades", "Has seleccionado la habilidad especial de Sigilo.")
        }
        val Espada = findViewById<CheckBox>(R.id.Espada)
        Sigilo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) Log.d("Habilidades", "Has seleccionado la habilidad especial de Espada.")
        }

        val btnRegistro = findViewById<ImageButton>(R.id.btnRegistro)

        //Función Lambda para registrar en el Logcat
        val registrarEnLogcat = { nombre: String, raza: String, faccion: String, sigilo: Boolean, espada: Boolean ->
            Log.d("Guerrero", """
                PERSONAJE CREADO:
                - Nombre: $nombre
                - Raza: $raza
                - Facción: $faccion
                - Habilidades: Sigilo=$sigilo, Espada=$espada
            """.trimIndent())
        }

        //Configuramos el click del ImageButton
        btnRegistro.setOnClickListener {
            //Extraemos los datos
            val nombre = Nombre.text.toString()
            val raza = spinnerRaza.selectedItem.toString()

            val idSeleccionado = Faccion.checkedRadioButtonId
            val faccion = if (idSeleccionado == R.id.rbComunidad) "Comunidad del Anillo" else "Huestes de Mordor"

            val tieneSigilo = Sigilo.isChecked
            val tieneEspada = Espada.isChecked

            if (nombre.isNotBlank()) {
                //Disparar Toast
                val textoToast = "Guerrero $nombre ($raza) alistado en $faccion"
                Toast.makeText(this, textoToast, Toast.LENGTH_LONG).show()

                //Ejecutar la función lambda para el Logcat
                registrarEnLogcat(nombre, raza, faccion, tieneSigilo, tieneEspada)
            } else {
                Toast.makeText(this, "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
            }
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
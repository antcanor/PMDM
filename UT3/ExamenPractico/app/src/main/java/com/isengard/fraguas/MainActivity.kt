package com.isengard.fraguas

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

class MainActivity : AppCompatActivity() {
    private val TAG = "FraguasIsengard"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val identificador = findViewById<EditText>(R.id.etIdentificador)
        identificador.requestFocus()
        identificador.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                // Validar si el campo se quedó vacío al abandonarlo
                if (identificador.text.isEmpty()) identificador.error = "¡No se admiten soldados anónimos!"
            }
        }

        val tipoUnidad = findViewById<Spinner>(R.id.tipoUnidad)
        tipoUnidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val unidadSeleccionada = parent?.getItemAtPosition(position).toString()
                Log.d("Soldado", "Tipo Unidad seleccionada: $unidadSeleccionada")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val Escudos = findViewById<RadioGroup>(R.id.Escudos)
        Escudos.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbEscudo -> Toast.makeText(this, "¡Se ha elegido Escudo!", Toast.LENGTH_SHORT).show()
                R.id.rbArmadura -> Toast.makeText(this, "¡Se ha elegido Armadura!", Toast.LENGTH_SHORT).show()
            }
        }

        val Antorcha = findViewById<CheckBox>(R.id.Antorcha)
        Antorcha.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) Log.d("Soldado", "Has seleccionado la Antorcha de Polvorín.")
        }


        val btnRegistro = findViewById<ImageButton>(R.id.btnRegistro)

        btnRegistro.setOnClickListener {
            //Extraemos los datos
            val nombre = identificador.text.toString()
            val tipoUnidad = tipoUnidad.selectedItem.toString()

            val idSeleccionado = Escudos.checkedRadioButtonId
            val escudo = if (idSeleccionado == R.id.rbEscudo) "Escudo" else "Armadura"

            val tienenAntorcha = Antorcha.isChecked

            if (nombre.isNotBlank()) {
                //Disparar Toast
                val textoToast = "Unidad $nombre ($tipoUnidad) enviada al Abismo de Helm"
                Toast.makeText(this, textoToast, Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
            }
        }



    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(): Las fraguas se encienden.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(): Las Fraguas vuelven a recuperar su potencia.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(): Saruman detiene la producción temporalmente.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop(): Se han apagado las fraguas.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(): La fragua ha sido destruida por completo.")
    }
}
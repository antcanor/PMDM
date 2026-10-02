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

    lateinit var identificador : EditText
    lateinit var tipoUnidad : Spinner
    lateinit var Escudos : RadioGroup
    lateinit var Antorcha: CheckBox
    lateinit var btnRegistro: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        identificador = findViewById<EditText>(R.id.etIdentificador)
        identificador.requestFocus()
        identificador.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                // Validar si el campo se quedó vacío al abandonarlo
                if (identificador.text.isEmpty()) identificador.error = "¡No se admiten soldados anónimos!"
            }
        }

        tipoUnidad = findViewById<Spinner>(R.id.tipoUnidad)
        tipoUnidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val unidadSeleccionada = parent?.getItemAtPosition(position).toString()
                Log.d("Soldado", "Tipo Unidad seleccionada: $unidadSeleccionada")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        Escudos = findViewById<RadioGroup>(R.id.Escudos)
        Escudos.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbEscudo -> Toast.makeText(this, "¡Se ha elegido Escudo!", Toast.LENGTH_SHORT).show()
                R.id.rbArmadura -> Toast.makeText(this, "¡Se ha elegido Armadura!", Toast.LENGTH_SHORT).show()
            }
        }

        Antorcha = findViewById<CheckBox>(R.id.Antorcha)
        Antorcha.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) Log.d("Soldado", "Has seleccionado la Antorcha de Polvorín.")
        }


        btnRegistro = findViewById<ImageButton>(R.id.btnRegistro)

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("key_identificador",identificador.text.toString())
        outState.putInt("key_tipo_unidad", tipoUnidad.selectedItemPosition)
        outState.putInt("key_escudo", Escudos.checkedRadioButtonId)
        outState.putBoolean("key_antorcha", Antorcha.isChecked)

        Log.d(TAG, "onSaveInstanceState(): Guardando el progreso de creación del Uruk-hai.")

        super.onSaveInstanceState(outState)
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
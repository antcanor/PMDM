package com.isengard.elregistrodeguardiasdelaciudadela

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PreferenciasActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var etNombre: EditText
    private lateinit var rgRango: RadioGroup
    private lateinit var swEstado: Switch
    private lateinit var btnGuardar: Button
    private lateinit var tvUltimoRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        // Abrimos (o creamos si no existe) el archivo RegistroGuardia.xml.
        // MODE_PRIVATE: solo nuestra app puede leerlo o modificarlo.
        prefs = getSharedPreferences("RegistroGuardia", Context.MODE_PRIVATE)

        etNombre = findViewById(R.id.etNombreGuardia)
        rgRango = findViewById<RadioGroup>(R.id.rgRango)
        swEstado = findViewById(R.id.swEstadoGuardia)
        btnGuardar = findViewById(R.id.btnGuardarPref)
        tvUltimoRegistro = findViewById(R.id.tvUltimoRegistro)

        // Cada vez que el usuario mueve el interruptor, cambiamos su texto
        swEstado.setOnCheckedChangeListener { _, activo ->
            swEstado.text = if (activo) "En Guardia" else "En Reposo"
        }
        btnGuardar.setOnClickListener { guardarCentinela() }

    }


    // onResume se ejecuta cada vez que la pantalla vuelve a verse:
    override fun onResume() {
        super.onResume()
        cargarFormulario()
        mostrarUltimoRegistro()
    }

    // Lee los datos guardados y rellena el formulario con ellos
    private fun cargarFormulario() {
        if (!prefs.contains("KEY_SOLDADO")) return

        // Si todavía no se ha guardado ningún soldado, no hacemos nada
        if (!prefs.contains("KEY_SOLDADO")) return

        // Ponemos el nombre guardado en la caja de texto
        etNombre.setText(prefs.getString("KEY_SOLDADO", ""))

        // Marcamos la opción de rango que corresponde al número guardado
        rgRango.check(
            when (prefs.getInt("KEY_RANGO", 0)) {
                1    -> R.id.rbCapitan
                2    -> R.id.rbVeterano
                else -> R.id.rbRecluta
            }
        )

        // Colocamos el interruptor como estaba y ajustamos su texto
        swEstado.isChecked = prefs.getBoolean("KEY_ESTADO", false)
        swEstado.text = if (swEstado.isChecked) "En Guardia" else "En Reposo"
    }
    // Recoge los datos del formulario y los guarda en el archivo
    private fun guardarCentinela() {
        // Cogemos el nombre y quitamos los espacios del principio y del final
        val nombre = etNombre.text.toString().trim()

        // Si el nombre está vacío, avisamos y no guardamos nada
        if (nombre.isEmpty()) {
            etNombre.error = "El nombre no puede estar vacío"
            etNombre.requestFocus()
            return
        }

        // Convertimos la opción marcada en un número: 0, 1 o 2
        val rango = when (rgRango.checkedRadioButtonId) {
            R.id.rbCapitan  -> 1
            R.id.rbVeterano -> 2
            else            -> 0
        }

        // true si el interruptor está activado (En Guardia), false si no (En Reposo)
        val estado = swEstado.isChecked

        // Abrimos el editor para poder escribir en el archivo
        val editor = prefs.edit()

        // Metemos cada dato con su clave obligatoria
        editor.putString("KEY_SOLDADO", nombre)
        editor.putInt("KEY_RANGO", rango)
        editor.putBoolean("KEY_ESTADO", estado)

        // apply() guarda los cambios en segundo plano, sin bloquear la app
        editor.apply()

        // Comprobación de seguridad: volvemos a leer los datos para confirmar
        // que se han guardado bien. Los valores por defecto (-1 y !estado) son
        // imposibles a propósito: si algo no se guardó, la comprobación falla.
        val guardadoOk = prefs.getString("KEY_SOLDADO", null) == nombre &&
                prefs.getInt("KEY_RANGO", -1) == rango &&
                prefs.getBoolean("KEY_ESTADO", !estado) == estado

        if (guardadoOk) {
            // Solo si todo está guardado, vaciamos la caja de texto
            etNombre.text.clear()
            mostrarUltimoRegistro()
            Toast.makeText(this, "Centinela registrado", Toast.LENGTH_SHORT).show()
        } else {
            // Si algo falló, dejamos los datos en pantalla para no perderlos
            Toast.makeText(this, "Error al guardar: los datos se mantienen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nombreRango(rango: Int): String = when (rango) {
        0 -> "Recluta"
        1 -> "Capitán"
        2 -> "Veterano"
        else -> "Desconocido"
    }

    private fun mostrarUltimoRegistro() {
        val nombre = prefs.getString("KEY_SOLDADO", null)

        if (nombre.isNullOrEmpty()) {
            tvUltimoRegistro.text = "Último centinela guardado: Ninguno"
            return
        }

        val rango  = prefs.getInt("KEY_RANGO", 0)
        val estado = prefs.getBoolean("KEY_ESTADO", false)

        tvUltimoRegistro.text = "Último guardia guardado: $nombre | Rango: ${nombreRango(rango)} - ${if (estado) "En Guardia" else "En Reposo"}"
    }

}
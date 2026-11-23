package com.isengard.elregistrodeguardiasdelaciudadela

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileNotFoundException

class FicherosActivity : AppCompatActivity() {
    private enum class Destino { INTERNO, EXTERNO }

    private lateinit var tvEstadoMedio: TextView
    private lateinit var etNombre: EditText
    private lateinit var etContenido: EditText
    private lateinit var rgDestino: RadioGroup
    private lateinit var btnGuardar: Button
    private lateinit var btnLeer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficheros)

        // 1. Vinculamos los componentes de la pantalla
        val tvEstadoMedio = findViewById<TextView>(R.id.tvEstadoMedio)
        val etNombre = findViewById<EditText>(R.id.etNombrePergamino)
        val etContenido = findViewById<EditText>(R.id.etContenido)
        val rbInterno = findViewById<RadioButton>(R.id.rbInterno)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnLeer = findViewById<Button>(R.id.btnLeer)


        // 2. Mostramos el estado de la memoria externa al abrir la pantalla
        if (esExternoEscribible()) {
            tvEstadoMedio.text = "Memoria externa disponible"
        } else {
            tvEstadoMedio.text = "Memoria externa no disponible"
        }


        // 3. Botón GUARDAR
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val texto = etContenido.text.toString()

            if (nombre.isEmpty()) {
                etNombre.error = "Especifica el nombre del pergamino"
                return@setOnClickListener
            }

            try {
                if (rbInterno.isChecked) {
                    // alamcenamiento interno
                    openFileOutput(nombre, Context.MODE_PRIVATE).use { flujo ->
                        flujo.write(texto.toByteArray())
                    }
                    Toast.makeText(this, "Pergamino guardado en la bóveda interna", Toast.LENGTH_SHORT).show()
                } else {
                    //  almacenamiento externo
                    if (!esExternoEscribible()) {
                        Toast.makeText(this, "La memoria externa no está montada", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    val archivo = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), nombre)
                    archivo.writeText(texto)
                    Toast.makeText(this, "Mapa guardado en Documents/", Toast.LENGTH_SHORT).show()
                }
                etContenido.text.clear()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Botón LEER
        btnLeer.setOnClickListener {
            val nombre = etNombre.text.toString().trim()

            if (nombre.isEmpty()) {
                etNombre.error = "Indica qué pergamino quieres leer"
                return@setOnClickListener
            }

            try {
                if (rbInterno.isChecked) {
                    // almacenamiento interno
                    val texto = openFileInput(nombre).bufferedReader().use { lector ->
                        lector.readText()
                    }
                    etContenido.setText(texto)
                } else {
                    // almacenamiento externo
                    if (!esExternoLeible()) {
                        Toast.makeText(this, "La memoria externa no está disponible", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    val archivo = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), nombre)
                    etContenido.setText(archivo.readText())
                }
                Toast.makeText(this, "Pergamino leído correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: FileNotFoundException) {
                // El fichero no existe (vale para interno y externo)
                etNombre.error = "Ese pergamino no existe"
                Toast.makeText(this, "Fichero no encontrado", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al leer: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun esExternoEscribible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return estado == Environment.MEDIA_MOUNTED
    }

    private fun esExternoLeible(): Boolean {
        val estado = Environment.getExternalStorageState()
        return estado == Environment.MEDIA_MOUNTED || estado == Environment.MEDIA_MOUNTED_READ_ONLY
    }
}
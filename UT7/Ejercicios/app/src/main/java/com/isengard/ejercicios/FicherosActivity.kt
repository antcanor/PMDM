package com.isengard.ejercicios

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException

class FicherosActivity : AppCompatActivity() {
    private lateinit var etNombreArchivo: EditText
    private lateinit var etContenido: EditText
    private lateinit var btnEscribir: Button
    private lateinit var btnLeer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficheros)

        // 1. Vinculamos componentes gráficos de la memoria privada
        etNombreArchivo = findViewById(R.id.etNombreArchivo)
        etContenido = findViewById(R.id.etContenidoFichero)
        btnEscribir = findViewById(R.id.btnEscribirFichero)
        btnLeer = findViewById(R.id.btnLeerFichero)

        // 2. Operación de ESCRITURA en almacenamiento específico de la app
        btnEscribir.setOnClickListener {
            val nombre = etNombreArchivo.text.toString().trim()
            val texto = etContenido.text.toString()

            if (nombre.isNotEmpty()) {
                try {
                    // MODE_PRIVATE garantiza aislamiento absoluto frente a otras apps
                    openFileOutput(nombre, Context.MODE_PRIVATE).use { flujoSalida ->
                        flujoSalida.write(texto.toByteArray())
                    }
                    Toast.makeText(this, "Pergamino sellado con éxito en files/", Toast.LENGTH_SHORT).show()
                    etContenido.text.clear() // Limpiamos la consola tras guardar
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al escribir en el disco: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreArchivo.error = "Especifica un nombre para el pergamino"
            }
        }

        // 3. Operación de LECTURA desde almacenamiento específico de la app
        btnLeer.setOnClickListener {
            val nombre = etNombreArchivo.text.toString().trim()

            if (nombre.isNotEmpty()) {
                try {
                    // Abrimos el flujo de lectura y lo transformamos a texto plano de vuelta
                    val textoLeido = openFileInput(nombre).bufferedReader().use { lector ->
                        lector.readText()
                    }
                    etContenido.setText(textoLeido)
                    Toast.makeText(this, "Pergamino leído correctamente", Toast.LENGTH_SHORT).show()
                } catch (e: FileNotFoundException) {
                    etNombreArchivo.error = "El archivo especificado no existe en la ciudadela"
                    Toast.makeText(this, "Fichero no encontrado", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error de E/S: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombreArchivo.error = "Introduce el nombre del pergamino a leer"
            }
        }
    }
}
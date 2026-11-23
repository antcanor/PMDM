package com.isengard.elregistrodeguardiasdelaciudadela

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.isengard.elregistrodeguardiasdelaciudadela.helpers.AdminSQLiteOpenHelper
import kotlin.compareTo

class SQLiteActivity : AppCompatActivity(){
    private lateinit var etNombre: EditText
    private lateinit var etSoldados: EditText
    private lateinit var btnInsertar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnConsultar: Button
    private lateinit var tvConsola: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        etNombre = findViewById(R.id.etNombreBatallon)
        etSoldados = findViewById(R.id.etNumeroSoldados)
        btnInsertar = findViewById(R.id.btnInsertarBD)
        btnEliminar = findViewById(R.id.btnEliminarBD)
        btnConsultar = findViewById(R.id.btnConsultarBD)
        tvConsola = findViewById(R.id.tvConsolaBD)

        // Instanciamos el Helper con versión de esquema 1
        val helper = AdminSQLiteOpenHelper(this, "ejercito.db", null, 1)

        // 1. OPERACIÓN: INSERCIÓN (CREATE / UPDATE)
        btnInsertar.setOnClickListener {
            val db = helper.writableDatabase // Solicitamos permisos de escritura física
            val nombre = etNombre.text.toString().trim()
            val soldadosStr = etSoldados.text.toString().trim()

            if (nombre.isNotEmpty() && soldadosStr.isNotEmpty()) {
                val registro = ContentValues().apply {
                    put("nombre", nombre)
                    put("soldados", soldadosStr.toInt())
                }

                // Inserción limpia usando wrappers estructurados del framework
                val resultado = db.insertWithOnConflict(
                    "batallones",
                    null,
                    registro,
                    SQLiteDatabase.CONFLICT_REPLACE // Si ya existe la PK, la actualiza
                )

                if (resultado != -1L) {
                    Toast.makeText(this, "Tropa registrada en el censo real", Toast.LENGTH_SHORT).show()
                    etNombre.text.clear()
                    etSoldados.text.clear()
                    btnConsultar.performClick() // Refrescamos consola automáticamente
                } else {
                    Toast.makeText(this, "Error de escritura en el registro", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa ambos campos tácticos", Toast.LENGTH_SHORT).show()
            }
            db.close() // Cerramos la conexión para evitar fugas de memoria
        }

        // 2. OPERACIÓN: ELIMINACIÓN (DELETE)
        btnEliminar.setOnClickListener {
            val db = helper.writableDatabase
            val nombre = etNombre.text.toString().trim()

            if (nombre.isNotEmpty()) {
                // delete() devuelve la cantidad de filas afectadas en la transacción
                val cantidadAfectada = db.delete("batallones", "nombre=?", arrayOf(nombre))
                db.close()

                etNombre.text.clear()
                etSoldados.text.clear()

                if (cantidadAfectada > 0) {
                    Toast.makeText(this, "Batallón licenciado del ejército", Toast.LENGTH_SHORT).show()
                    btnConsultar.performClick()
                } else {
                    Toast.makeText(this, "No existe tal batallón bajo registro", Toast.LENGTH_SHORT).show()
                }
            } else {
                etNombre.error = "Especifica el batallón a dar de baja"
            }
        }

        // 3. OPERACIÓN: CONSULTA COMPLETA (READ)
        btnConsultar.setOnClickListener {
            val db = helper.readableDatabase // Modo lectura eficiente
            val cursor = db.rawQuery("SELECT nombre, soldados FROM batallones ORDER BY soldados DESC", null)

            val sb = StringBuilder()
            sb.append("📋 REPORTE DE FUERZAS OPERATIVAS:\n")
            sb.append("=================================\n\n")

            // Recorremos el cursor secuencialmente fila por fila
            if (cursor.moveToFirst()) {
                do {
                    val nombre = cursor.getString(0) // Índice de columna 0 (nombre)
                    val soldados = cursor.getInt(1)  // Índice de columna 1 (soldados)
                    sb.append("• $nombre -> 👤 Efectivos: $soldados\n")
                } while (cursor.moveToNext())
            } else {
                sb.append("Las tierras están en calma. No hay batallones registrados.")
            }

            tvConsola.text = sb.toString()
            cursor.close() // Obligatorio cerrar el cursor de memoria
            db.close()
        }
    }
}
package com.isengard.larutahacialamontaadeldestino

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem




class MainActivity : AppCompatActivity() {
    private lateinit var listaEtapas: List<Etapa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Creamos las etapas con sus datros
        listaEtapas = listOf(
            Etapa("La Comarca (Hobbiton)", "Bajo", "comarca"),
            Etapa("Rivendell", "Bajo", "rivendell"),
            Etapa("Minas de Moria", "Muy Alto", "minas"),
            Etapa("Lothlórien", "Medio", "lothlorien"),
            Etapa("Paso de Cirith Ungol", "Extremo", "ungol"),
            Etapa("Monte del Destino (Mordor)", "Crítico", "monte")
        )
        // Vinculamos el Listview y le acoplamos el adaptador
        val lvEtapas = findViewById<ListView>(R.id.lvEtapas)
        val adapter = EtapaAdapter(this, listaEtapas)
        lvEtapas.adapter = adapter
        // Registramos el listview para que acepte el menu contextual
        registerForContextMenu(lvEtapas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)
        return true
    }
    // Gestiona los clics en el menú superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opc_abandonar -> {
                Toast.makeText(this, "Abandonando la misión...", Toast.LENGTH_SHORT).show()
                // Cierra la actividad y la app de forma controlada
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Infla el menú contextual cuando se mantiene pulsada una fila
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.setHeaderTitle("Acciones de Territorio")
        menuInflater.inflate(R.menu.menu_contextual_etapa, menu)
    }
    // Captura el clic sobre la opción seleccionada en el menú flotante
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Recuperamos la información de la fila que recibió la pulsación larga
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val etapaSeleccionada = listaEtapas[info.position]
        return when (item.itemId) {
            R.id.ctx_inspeccionar -> {
                // Abrimos DetalleActivity enviando datos mediante un Intent Explícito
                val intent = Intent(this, DetalleActivity::class.java).apply {
                    putExtra("EXTRA_NOMBRE", etapaSeleccionada.nombre)
                    putExtra("EXTRA_PELIGRO", etapaSeleccionada.peligro)
                }
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
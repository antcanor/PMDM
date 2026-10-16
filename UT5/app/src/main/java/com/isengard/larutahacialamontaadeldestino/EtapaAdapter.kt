package com.isengard.larutahacialamontaadeldestino

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class EtapaAdapter (
    private val context: Context,
    private val listaEtapas: List<Etapa>
) : BaseAdapter() {
    //Devuelve el número total de elementos que tiene la lista
    override fun getCount(): Int = listaEtapas.size

    //Devuelve el objeto Etapa en una posición concreta
    override fun getItem(position: Int): Any = listaEtapas[position]

    //Devuelve un identificador único para la fila (normalmente se usa la propia posición)
    override fun getItemId(position: Int): Long = position.toLong()

    //Se llama para dibujar cada fila en la pantalla
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 1. Reutilizamos la vista si ya existe, si no, la inflamos desde el XML
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.etapa_fila, parent, false)

        // 2. Extraemos el personaje correspondiente a la fila actual
        val etapa = listaEtapas[position]

        // 3. Vinculamos los componentes del XML de la fila
        val ivEtapa = view.findViewById<ImageView>(R.id.ivEtapa)
        val tvNombreEtapa = view.findViewById<TextView>(R.id.tvNombreEtapa)
        val tvPeligroEtapa = view.findViewById<TextView>(R.id.tvPeligroEtapa)

        // 4. Inyectamos los datos del objeto en la vista
        tvNombreEtapa.text = etapa.nombre
        tvPeligroEtapa.text = "Nivel de peligro: ${etapa.peligro}"
        val idRecurso = context.resources.getIdentifier(
            etapa.imagenId,
            "drawable",
            context.packageName
        )
        if (idRecurso != 0) {
            ivEtapa.setImageResource(idRecurso)
        } else {
            ivEtapa.setImageResource(android.R.drawable.ic_menu_mapmode) // Imagen de reserva
        }
        return view
    }
}
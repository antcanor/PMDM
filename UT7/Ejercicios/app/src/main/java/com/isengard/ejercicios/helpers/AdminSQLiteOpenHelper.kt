package com.isengard.ejercicios.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(
context: Context,
name: String,
factory: SQLiteDatabase.CursorFactory?,
version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    // Se ejecuta de forma automática cuando el archivo físico .db no existe en el sistema
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE batallones (" +
                    "nombre TEXT PRIMARY KEY, " +
                    "soldados INTEGER)"
        )
    }

    // Se dispara cuando la versión de la base de datos (DB_VERSION) se incrementa en el constructor
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS batallones")
        onCreate(db)
    }
}
package com.isengard.elregistrodeguardiasdelaciudadela.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(context: Context,
                            name: String,
                            factory: SQLiteDatabase.CursorFactory?,
                            version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE batallones (" +
                    "nombre TEXT PRIMARY KEY, " +
                    "soldados INTEGER)"
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS batallones")
        onCreate(db)
    }
}
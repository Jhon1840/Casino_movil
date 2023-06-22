package com.Jhon.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "UsuariosDB"
        private const val TABLE_NAME = "Usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USUARIO = "usuario"
        private const val COLUMN_CONTRASENA = "contrasena"
        private const val COLUMN_CORREO = "correo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_USUARIO TEXT, $COLUMN_CONTRASENA TEXT, $COLUMN_CORREO TEXT)")
        db.execSQL(CREATE_TABLE)
        insertInitialUser(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertInitialUser(db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_USUARIO, "jhon")
        values.put(COLUMN_CONTRASENA, "123")
        values.put(COLUMN_CORREO, "jhons@gmail.com")
        db.insert(TABLE_NAME, null, values)
    }

    fun insertUsuario(usuario: String, contrasena: String, correo: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USUARIO, usuario)
        values.put(COLUMN_CONTRASENA, contrasena)
        values.put(COLUMN_CORREO, correo)
        return db.insert(TABLE_NAME, null, values)
    }

    fun checkCredentials(usuario: String, contrasena: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COLUMN_USUARIO = ? AND $COLUMN_CONTRASENA = ?"
        val selectionArgs = arrayOf(usuario, contrasena)
        val cursor: Cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val credentialsMatch = cursor.count > 0
        cursor.close()
        return credentialsMatch
    }

    fun printUserData() {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val usuario = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO))
                val contrasena = cursor.getString(cursor.getColumnIndex(COLUMN_CONTRASENA))
                val correo = cursor.getString(cursor.getColumnIndex(COLUMN_CORREO))
                Log.d("DatabaseHelper", "Usuario: $usuario, Contraseña: $contrasena, Correo: $correo")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}

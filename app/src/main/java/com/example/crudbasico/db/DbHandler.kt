package com.example.crudbasico.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crudbasico.model.Person

class DbHandler (ctx: Context): SQLiteOpenHelper(ctx,DB_NAME,null,DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($CPF INTEGER PRIMARY KEY, $NAME TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addPessoa(pessoa: Person): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, pessoa.nome)
        val _success = db.insert(TABLE_NAME,null,values)
        return (("$_success").toInt() != -1)
    }

    fun getPessoa(_cpf: String): Person {
        val pessoa = Person()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $CPF = $_cpf"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        pessoa.cpf = cursor.getString(cursor.getColumnIndex(CPF))
        pessoa.nome = cursor.getString(cursor.getColumnIndex(NAME))
        cursor.close()
        return pessoa
    }

    fun pessoas(): ArrayList<Person> {
        val pessoaList = ArrayList<Person>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val pessoa = Person()
                    pessoa.cpf = cursor.getString(cursor.getColumnIndex(CPF))
                    pessoa.nome = cursor.getString(cursor.getColumnIndex(NAME))
                    pessoaList.add(pessoa)
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return pessoaList
    }

    fun updatePessoa(pessoa: Person): Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAME, pessoa.nome)
        }
        val _success = db.update(TABLE_NAME, values, CPF + "=?", arrayOf(pessoa.cpf)).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deletePessoa(_cpf: String): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, CPF + "=?", arrayOf(_cpf)).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAllPessoa(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "cadastro_db"
        private val TABLE_NAME = "Pessoa"
        private val CPF = "CPF"
        private val NAME = "Nome"
    }
}
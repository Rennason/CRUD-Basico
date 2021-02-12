package com.example.crudbasico

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudbasico.db.DbHandler
import com.example.crudbasico.model.Notas

class NameActivity : AppCompatActivity() {

    // Database
    val databaseHandler = DbHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        val edit = intent.getBooleanExtra("edit", false)
        val position = intent.getIntExtra("position", 0)
        val btnInsertNome = findViewById<Button>(R.id.btnInsertNome)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val etNome = findViewById<EditText>(R.id.editText)

        if(edit){
            val nota = databaseHandler.getNota(position)
            etNome.setText(nota.descricao)
            btnInsertNome.setText("Editar")
        }
        btnInsertNome.setOnClickListener {
            if(etNome.text.toString() == ""){
                Toast.makeText(this,"Descrição vazia.",Toast.LENGTH_SHORT).show()
            }
            else {
                if(edit){
                    val nota = Notas(position, etNome.text.toString())
                    databaseHandler.updateNota(nota)
                    finish()
                }
                else {
                    val nota = Notas(0, etNome.text.toString())
                    databaseHandler.addNota(nota)
                    finish()
                }
            }
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
}
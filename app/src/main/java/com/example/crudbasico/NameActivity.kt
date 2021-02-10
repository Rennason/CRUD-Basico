package com.example.crudbasico

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudbasico.db.DbHandler
import com.example.crudbasico.model.Person

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
        var positionStr = position.toString()

        if(edit){
            val pessoa = databaseHandler.getPessoa(positionStr)
            etNome.setText(pessoa.nome)
            btnInsertNome.setText("Editar")
        }
        btnInsertNome.setOnClickListener {
            if(etNome.text.toString() == ""){
                Toast.makeText(this,"Nome está vazio.",Toast.LENGTH_SHORT).show()
            }
            else {
                if(edit){
                    val pessoa = Person(positionStr, etNome.text.toString())
                    databaseHandler.updatePessoa(pessoa)
                    finish()
                }
                else {
                    val pessoa = Person("0", etNome.text.toString())
                    databaseHandler.addPessoa(pessoa)
                    finish()
                }
            }
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
}
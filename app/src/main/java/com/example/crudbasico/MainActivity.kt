package com.example.crudbasico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudbasico.adapter.ListAdapter
import com.example.crudbasico.db.DbHandler
import com.example.crudbasico.model.Notas

class MainActivity : AppCompatActivity() {

    var listaAdapter: ListAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    val btnInsert by lazy { findViewById<Button>(R.id.btnInsert) }
    val recyclerview by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    var pessoaList = ArrayList<Notas>()
    // SQLite - Banco Local no celular
    var databaseHandler = DbHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        btnInsert.setOnClickListener {
            val intent = Intent(this, NameActivity::class.java)

            startActivityForResult(intent,1)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView(){
        pessoaList = databaseHandler.notas()
        listaAdapter = ListAdapter(pessoaList,this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = listaAdapter
    }
    private fun deleteAdapter(position: Int){
        pessoaList.removeAt(position)
        listaAdapter!!.notifyItemRemoved(position)
    }
}
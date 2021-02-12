package com.example.crudbasico.adapter

import com.example.crudbasico.db.DbHandler
import com.example.crudbasico.model.Notas
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudbasico.NameActivity
import com.example.crudbasico.R
import kotlinx.android.synthetic.main.content_list.view.*

class ListAdapter (nameList: List<Notas>, internal var ctx: Context, private val callbacks: (Int) -> Unit): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    internal var nameList: List<Notas> = ArrayList<Notas>()
    init {
        this.nameList = nameList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.content_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = nameList[position]

        holder.name.text = name.descricao

        holder.name.setOnClickListener {
            val intent = Intent(ctx, NameActivity::class.java)
            intent.putExtra("edit", true)
            intent.putExtra("position", name.id)
            ctx.startActivity(intent)
        }
        holder.btn.setOnClickListener {
            val databaseHandler = DbHandler(ctx)
            databaseHandler.deleteNota(name.id)
            callbacks(position)
        }
    }


    override fun getItemCount(): Int {
        return nameList.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name: TextView = view.tvAdpNome
        var btn: Button = view.btnAdpDel
    }
}
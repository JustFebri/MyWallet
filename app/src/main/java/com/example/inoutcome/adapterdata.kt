package com.example.inoutcome

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterdata (
    private val listany: ArrayList<data>
) : RecyclerView.Adapter<adapterdata.ListViewHolder>()
{

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var kategori : TextView = itemView.findViewById(R.id.txtkat1)
        var tipe : TextView = itemView.findViewById(R.id.txttp1)
        var tanggal : TextView = itemView.findViewById(R.id.txttgl1)
        var currency: TextView = itemView.findViewById(R.id.txtcur1)
        var rp: TextView = itemView.findViewById(R.id.rp1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder{
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_rcv, parent, false)
        return  ListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return  listany.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var any = listany[position]
        holder.currency.setText(any.currency.toString())
        if(any.tipe == "Income"){
            holder.tipe.setText("+ ")
            holder.tipe.setTextColor(Color.parseColor("#00FF00"))
            holder.currency.setTextColor(Color.parseColor("#00FF00"))
            holder.rp.setTextColor(Color.parseColor("#00FF00"))
        }
        else if(any.tipe == "Outcome"){
            holder.tipe.setText("-")
            holder.tipe.setTextColor(Color.parseColor("#FF0000"))
            holder.currency.setTextColor(Color.parseColor("#FF0000"))
            holder.rp.setTextColor(Color.parseColor("#FF0000"))
        }
        holder.kategori.setText(any.kategori)
        holder.tanggal.setText(any.tanggal)
    }

}
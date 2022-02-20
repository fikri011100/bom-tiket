package bncc.net.bom.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bncc.net.bom.R
import kotlinx.android.synthetic.main.item_genre.view.*
import java.util.*

class GenreAdapter(var data : Vector<String>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
            fun setData( s:String){
                    view.btn_genre.text = s;
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size;
    }

}
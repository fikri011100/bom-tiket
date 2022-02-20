package bncc.net.bom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import bncc.net.bom.R
import bncc.net.bom.model.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*

class MovieAdapter(var data : Vector<Movie>, var onclick : (layout : ConstraintLayout, movie : Movie)-> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
            fun setData( m :Movie){
                    view.tv_title.text = m.title;
                    view.tv_rating.text = m.imDbRating;
                    Glide.with(view).load(m.image).into(view.iv_image);
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data.get(position))
        onclick(holder.view.cl_movie,data.get(position) )
    }

    override fun getItemCount(): Int {
        return data.size;
    }

}
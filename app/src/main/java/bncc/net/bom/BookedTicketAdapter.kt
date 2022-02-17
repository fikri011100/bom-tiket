package bncc.net.bom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class BookedTicketAdapter(private val bookedTickets:ArrayList<String>) : RecyclerView.Adapter<BookedTicketAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_booked_movie:ImageView
        val tv_booked_title:TextView
        val tv_booked_time:TextView
        val tv_booked_seat:TextView

        init {
            iv_booked_movie = view.findViewById(R.id.iv_booked_movie)
            tv_booked_title = view.findViewById(R.id.tv_booked_title)
            tv_booked_time = view.findViewById(R.id.tv_booked_time)
            tv_booked_seat = view.findViewById(R.id.tv_booked_seat)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.booked_ticket_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_booked_title.text = bookedTickets[position]
    }

    override fun getItemCount() = bookedTickets.size
}
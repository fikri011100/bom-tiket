package bncc.net.bom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class BookedTicketAdapter(private val bookedTickets:ArrayList<Ticket>) : RecyclerView.Adapter<BookedTicketAdapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
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

        fun setData(booked: Ticket, numOfSeat:Int, seatText:String) {
            tv_booked_title.text = booked.title
            tv_booked_time.text = booked.time + ", " + booked.date
            tv_booked_seat.text = "" + numOfSeat + " Ticket (" + seatText + ")"
            Glide.with(view).load(booked.image).into(iv_booked_movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.booked_ticket_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val seat = bookedTickets[position].seat
        var seatText = "Seat " + seat
        var seatSize = 0

        if (seat != null) {
            seatSize = seat.split(", ").toTypedArray().size
        }

        holder.setData(bookedTickets[position], seatSize, seatText)
    }

    override fun getItemCount() = bookedTickets.size
}
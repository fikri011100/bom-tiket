package bncc.net.bom.ui.booking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bncc.net.bom.R

class SelectedSeatAdapter(private val selectedSeats:ArrayList<String>, private val listener: OnSeatAdapterListener) : RecyclerView.Adapter<SelectedSeatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSelectedSeat: TextView
        val btnRemove: Button

        init {
            tvSelectedSeat = view.findViewById(R.id.tv_seat_item)
            btnRemove = view.findViewById(R.id.btn_remove_seat)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.selected_seat_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSelectedSeat.text = selectedSeats[position]

        holder.btnRemove.setOnClickListener {
            selectedSeats.removeAt(position)
            listener.onRemovedData(selectedSeats.size)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = selectedSeats.size

    interface OnSeatAdapterListener {
        fun onRemovedData(numOfSeat:Int)
    }
}
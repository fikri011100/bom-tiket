package bncc.net.bom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class SelectedSeatAdapter(private val selectedSeats:ArrayList<String>, private val listener:OnSeatAdapterListener) : RecyclerView.Adapter<SelectedSeatAdapter.ViewHolder>() {

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
    ): SelectedSeatAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.selected_seat_item, parent, false)
        return SelectedSeatAdapter.ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SelectedSeatAdapter.ViewHolder, position: Int) {
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
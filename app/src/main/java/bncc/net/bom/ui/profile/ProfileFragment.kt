package bncc.net.bom.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import bncc.net.bom.ui.booking.BookedTicketAdapter
import bncc.net.bom.R
import bncc.net.bom.model.Ticket
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private val bookedTicketList = ArrayList<Ticket>()
    private lateinit var database: DatabaseReference
    private lateinit var ticketAdapter: BookedTicketAdapter
    private var numOfTicket = 0
    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = "1"

        database = FirebaseDatabase.getInstance("https://bom-ticket-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()




        tv_loading.visibility = View.VISIBLE
        tv_loading.text = "Loading result..."

        database.child("User").child(username).get().addOnSuccessListener {
            tv_name.text = it.child("nama").value.toString()
            tv_balance.text = "IDR " + it.child("saldo").value.toString()
            Glide.with(this).load(it.child("url").value.toString()).into(iv_profile)
        }.addOnFailureListener {

        }

        database.child("User").child(username).child("numofticket").get().addOnSuccessListener {
            if(it.exists()) {
                numOfTicket = Integer.parseInt(it.value.toString())

                database.child("User").child(username).child("tickets").get().addOnSuccessListener {
                    for(i in 1..numOfTicket) {
                        val ticket = Ticket()
                        ticket.title = it.child(i.toString()).child("title").value.toString()
                        ticket.image = it.child(i.toString()).child("image").value.toString()
                        ticket.date = it.child(i.toString()).child("date").value.toString()
                        ticket.time = it.child(i.toString()).child("time").value.toString()
                        ticket.ticketId = it.child(i.toString()).child("ticketId").value.toString()
                        ticket.seat = it.child(i.toString()).child("seat").value.toString()

                        bookedTicketList.add(ticket)
                    }
                    tv_loading.visibility = View.INVISIBLE
                    ticketAdapter.notifyDataSetChanged()

                }.addOnFailureListener {

                }
            }
            else {
                tv_loading.text = "You have no booked ticket"
            }
        }.addOnFailureListener {
        }

        rv_booked_ticket.layoutManager = LinearLayoutManager(requireContext())
        ticketAdapter = BookedTicketAdapter(bookedTicketList)
        rv_booked_ticket.adapter = ticketAdapter
    }
}
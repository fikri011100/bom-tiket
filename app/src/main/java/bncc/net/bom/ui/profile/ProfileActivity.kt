package bncc.net.bom.ui.profile

import android.content.Intent
import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bncc.net.bom.R
import bncc.net.bom.model.Ticket
import bncc.net.bom.signin.SignInActivity
import bncc.net.bom.ui.booking.BookedTicketAdapter
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val bookedTicketList = ArrayList<Ticket>()
    private lateinit var database: DatabaseReference
    private lateinit var ticketAdapter: BookedTicketAdapter
    private lateinit var tvLoading:TextView
    private lateinit var ivProfile:ImageView
    private lateinit var tvName:TextView
    private lateinit var tvBalance:TextView
    private var numOfTicket = 0
    private lateinit var username:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var username = intent.extras?.getString("username").toString()

        database = FirebaseDatabase.getInstance("https://bom-ticket-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
        tvLoading = findViewById(R.id.tv_loading)
        ivProfile = findViewById(R.id.iv_profile)
        tvName = findViewById(R.id.tv_name)
        tvBalance = findViewById(R.id.tv_balance)
        tvLoading.visibility = View.VISIBLE
        tvLoading.text = "Loading result..."

        database.child("User").child(username).get().addOnSuccessListener {
            tvName.text = it.child("nama").value.toString()
            tvBalance.text = "IDR " + it.child("saldo").value.toString()
            Glide.with(this).load(it.child("url").value.toString()).into(ivProfile)
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
                    tvLoading.visibility = View.INVISIBLE
                    ticketAdapter.notifyDataSetChanged()

                }.addOnFailureListener {

                }
            }
            else {
                tvLoading.text = "You have no booked ticket"
            }
        }.addOnFailureListener {
        }

        val rv_booked_ticket = findViewById<RecyclerView>(R.id.rv_booked_ticket)
        rv_booked_ticket.layoutManager = LinearLayoutManager(this)
        ticketAdapter = BookedTicketAdapter(bookedTicketList)
        rv_booked_ticket.adapter = ticketAdapter

        back_btn.setOnClickListener {
            finish()
        }

        btn_signout.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

}
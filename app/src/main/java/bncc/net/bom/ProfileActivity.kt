package bncc.net.bom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {

    private val bookedTicketList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        insertDummyData()

        val rv_booked_ticket = findViewById<RecyclerView>(R.id.rv_booked_ticket)
        rv_booked_ticket.layoutManager = LinearLayoutManager(this)
        rv_booked_ticket.adapter = BookedTicketAdapter(bookedTicketList)
    }

    fun insertDummyData() {
        bookedTicketList.add("Spiderman 1")
        bookedTicketList.add("Spiderman 2")
        bookedTicketList.add("Spiderman 3")
    }
}
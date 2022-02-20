package bncc.net.bom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import bncc.net.bom.model.Ticket
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_booking_payment.*

class BookingTicketPaymentSuccessActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_ticket_payment_success)

        var username = intent.extras?.getString("username").toString()
        database = FirebaseDatabase.getInstance("https://bom-ticket-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

        var title = intent.extras?.getString("title")
        var payment = intent.extras?.getString("payment")
        var imageUrl = intent.extras?.getString("image")
        var bookedDate = intent.extras?.getString("bookedDate")
        var bookedTime = intent.extras?.getString("bookedTime")
        var bookedSeat = intent.extras?.getString("bookedSeat")
        var paymentType = intent.extras?.getString("paymentType")

        var ticketId = 0
        database.child(username).get().addOnSuccessListener {
            if (it.child("numofticket").exists()) {
                ticketId = Integer.parseInt(it.child("numofticket").value.toString())
                ticketId++
            } else {
                ticketId = 1
            }

            var convertSeat = bookedSeat?.split(", ")
            var seatNumberText = ""
            seatNumberText += convertSeat!![0]
            for (i in 1..(convertSeat.size - 1)) {
                seatNumberText = seatNumberText + ", " + convertSeat[i]
            }
            val ticket = Ticket(title, bookedDate, bookedTime, imageUrl, seatNumberText, ticketId.toString())

            database.child(username).child("tickets").child(ticketId.toString()).setValue(ticket).addOnSuccessListener {

                Toast.makeText(this, "Thank you for your order", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
            database.child(username).child("numofticket").setValue(ticketId.toString()).addOnSuccessListener {
                Log.i("TAG", "onCreate: SUCCESS CHANGE NUM OF TICKET")
            }.addOnFailureListener {  }
            Thread.sleep(3000)
        }.addOnFailureListener {}
    }
}
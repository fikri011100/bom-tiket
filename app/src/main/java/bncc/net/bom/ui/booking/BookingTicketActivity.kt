package bncc.net.bom.ui.booking

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bncc.net.bom.*
import bncc.net.bom.model.Ticket
import bncc.net.bom.ui.booking.adapter.SelectedSeatAdapter
import bncc.net.bom.ui.payment.BookingPaymentActivity
import bncc.net.bom.utils.dialog.DateBottomDialogFragment
import bncc.net.bom.utils.dialog.TimeBottomDialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookingTicketActivity : AppCompatActivity() {

    private var dummyTime:Array<String> = arrayOf("11.20", "13.20", "15.20", "17.20", "19.20")
    private var dummySeatLetter:Array<String> = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
    private var dummySeatNumber:Array<String> = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    private lateinit var etTime:EditText
    private lateinit var etDate:EditText
    private lateinit var ivMovie:ImageView
    private lateinit var tvMovieTitle:TextView
    private lateinit var tvPrice:TextView
    private var selectedSeatList = ArrayList<String>()
    private lateinit var rv_selected_seat:RecyclerView
    private lateinit var tv_selected_seat:TextView
    private lateinit var seatAdapter: SelectedSeatAdapter
    private lateinit var database:DatabaseReference
    private lateinit var imageUrl:String
    private lateinit var username:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_ticket)

        // TODO ("Butuh oper username dari pas user login buat akses firebase, ganti masukin ke variable username dibawah ini")
        username = intent.extras?.getString("username").toString()

        var price = intent.extras?.getString("price").toString()

        database = FirebaseDatabase.getInstance("https://bom-ticket-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
        // set data (image, title) data from movie detail activity
        imageUrl = intent.extras?.getString("image").toString()
        tvMovieTitle = findViewById(R.id.tv_movie_title)
        tvMovieTitle.setText(intent.extras?.getString("title").toString())
        tvPrice = findViewById(R.id.tv_price)
        tvPrice.setText("IDR " + price + "/ticket")
        ivMovie = findViewById(R.id.iv_movie)
        Glide.with(this).load(imageUrl).into(ivMovie)

        etTime = findViewById(R.id.et_time)
        etDate = findViewById(R.id.et_date)
        tv_selected_seat = findViewById(R.id.tv_selected_seat)
        val timeBottomDialogFragment = TimeBottomDialogFragment(dummyTime)
        val seatBottomDialogFragment = DateBottomDialogFragment(dummySeatLetter, dummySeatNumber)

        seatAdapter = SelectedSeatAdapter(selectedSeatList, object : SelectedSeatAdapter.OnSeatAdapterListener {
            override fun onRemovedData(numOfSeat: Int) {
                if (numOfSeat == 0) {
                    tv_selected_seat.setText("No Seat Selected")
                }
                else {
                    tv_selected_seat.setText("Number of Tickets: " + selectedSeatList.size)
                }
            }
        })

        rv_selected_seat = findViewById(R.id.rv_selected_seat)
        rv_selected_seat.layoutManager = LinearLayoutManager(this)
        rv_selected_seat.adapter = seatAdapter

        // set datepickerdialog and min max date selection
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, selYear, selMonth, selDay ->
            val dateFormat:SimpleDateFormat = SimpleDateFormat("MMM")
            etDate.setText("" + selDay + " " + dateFormat.format(selMonth).toString() + " " + selYear)
        }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])

        val minDate = Calendar.getInstance()
        calendar.set(minDate[Calendar.YEAR], minDate[Calendar.MONTH], minDate[Calendar.DAY_OF_MONTH])
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DATE, +7)
        calendar.set(maxDate.get(Calendar.YEAR), maxDate.get(Calendar.MONTH), maxDate.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        
        findViewById<EditText>(R.id.et_date).setOnClickListener {
            datePickerDialog.show()
        }

        findViewById<EditText>(R.id.et_time).setOnClickListener {
            timeBottomDialogFragment.show(supportFragmentManager, "Time Bottom Dialog")
        }

        findViewById<Button>(R.id.btn_add_seat).setOnClickListener {
            seatBottomDialogFragment.show(supportFragmentManager, "Seat Bottom Dialog")
        }

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.book_btn).setOnClickListener {
            // get data
            val date = etDate.text
            val time = etTime.text

            if(date.isEmpty()) {
                Toast.makeText(this, "Please Choose a Date", Toast.LENGTH_SHORT).show()
            }
            else if (time.isEmpty()) {
                Toast.makeText(this, "Please Choose a Time", Toast.LENGTH_SHORT).show()
            }
            else if (selectedSeatList.size == 0) {
                Toast.makeText(this, "Please Choose Minimum 1 Seat", Toast.LENGTH_SHORT).show()
            }
            else {
//                saveTicketToFirebase(date.toString(), time.toString())
                var seatNumberText = ""
                seatNumberText += selectedSeatList[0]
                for (i in 1..(selectedSeatList.size - 1)) {
                    seatNumberText = seatNumberText + ", " + selectedSeatList[i]
                }
                // intent to booking activity (payment)
                val intent = Intent(this, BookingPaymentActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("title", tvMovieTitle.text)
                intent.putExtra("price", price)
                intent.putExtra("image", imageUrl)
                intent.putExtra("bookedDate", date.toString())
                intent.putExtra("bookedTime", time.toString())
                intent.putExtra("bookedSeat", seatNumberText)
                startActivity(intent)
            }
        }

    }

    fun setTime(time:String) {
        etTime.setText(time)
    }

    fun addSeat(seat:String) {
        selectedSeatList.add(seat)
        tv_selected_seat.setText("Number of Tickets: " + selectedSeatList.size)
        seatAdapter.notifyDataSetChanged()
    }
}
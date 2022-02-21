package bncc.net.bom.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bncc.net.bom.BookingTicketPaymentSuccessActivity
import bncc.net.bom.R
import bncc.net.bom.utils.dialog.PaymentTypeDialogFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_booking_payment.*

class BookingPaymentActivity : AppCompatActivity(){

    lateinit var bottomSheet: PaymentTypeDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_payment)

        var bookedSeat = intent.extras?.getString("bookedSeat")
        var imageUrl = intent.extras?.getString("image")
        var username = intent.extras?.getString("username")

        tv_title.text = intent.extras?.getString("title")
        tv_price.text = "IDR " + intent.extras?.getString("price") + "/ticket"
        tv_date.text = intent.extras?.getString("bookedDate")
        tv_time.text = intent.extras?.getString("bookedTime")
        tv_seat.text = bookedSeat
        Glide.with(this).load(imageUrl).into(img_movie)

        var convertSeat = bookedSeat?.split(", ")?.toTypedArray()
        var totalPayment = Integer.parseInt(intent.extras?.getString("price")) * (convertSeat?.size
            ?: 0)

        tv_payment.text = "Rp. " + totalPayment
        tv_payment.text = "Rp. " + totalPayment

        img_back.setOnClickListener {
            onBackPressed()
        }

        cons_type_payment.setOnClickListener {
            bottomSheet = PaymentTypeDialogFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        booking.setOnClickListener {
            val intent = Intent(this, BookingTicketPaymentSuccessActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("title", tv_title.text)
            intent.putExtra("payment", tv_payment.text)
            intent.putExtra("image", imageUrl)
            intent.putExtra("bookedDate", tv_date.text)
            intent.putExtra("bookedTime", tv_time.text)
            intent.putExtra("bookedSeat", tv_seat.text)
            intent.putExtra("paymentType", tv_payment_type.text)
            startActivity(intent)
        }
    }

    fun setPaymentType(data: String?) {
        tv_payment_type.text = data
    }
}
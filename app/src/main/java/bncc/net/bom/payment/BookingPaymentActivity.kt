package bncc.net.bom.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import bncc.net.bom.R
import bncc.net.bom.utils.dialog.PaymentTypeDialogFragment
import kotlinx.android.synthetic.main.activity_booking_payment.*

class BookingPaymentActivity : AppCompatActivity(){

    lateinit var bottomSheet: PaymentTypeDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_payment)

        img_back.setOnClickListener {
            onBackPressed()
        }

        cons_type_payment.setOnClickListener {
            bottomSheet = PaymentTypeDialogFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }
    }

    fun setPaymentType(data: String?) {
        tv_payment_type.text = data
    }
}
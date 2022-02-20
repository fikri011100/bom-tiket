package bncc.net.bom.utils.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bncc.net.bom.R
import bncc.net.bom.ui.payment.BookingPaymentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_payment_type_dialog.*


class PaymentTypeDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_type_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_dana.setOnClickListener {
            goToBookingPayment(text_dana.text.toString())
        }
        text_gopay.setOnClickListener {
            goToBookingPayment(text_gopay.text.toString())
        }
        text_ovo.setOnClickListener {
            goToBookingPayment(text_ovo.text.toString())
        }
        text_shopee.setOnClickListener {
            goToBookingPayment(text_shopee.text.toString())
        }
    }

    fun goToBookingPayment(data: String) {
        (activity as BookingPaymentActivity?)?.setPaymentType(data)
        dismiss()
    }

}
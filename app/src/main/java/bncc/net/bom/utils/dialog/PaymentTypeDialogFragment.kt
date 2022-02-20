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
            (activity as BookingPaymentActivity?)?.setPaymentType(text_dana.text.toString())
            dismiss()
        }
        text_gopay.setOnClickListener {
            (activity as BookingPaymentActivity?)?.setPaymentType(text_gopay.text.toString())
            dismiss()
        }
        text_ovo.setOnClickListener {
            (activity as BookingPaymentActivity?)?.setPaymentType(text_ovo.text.toString())
            dismiss()
        }
        text_shopee.setOnClickListener {
            (activity as BookingPaymentActivity?)?.setPaymentType(text_shopee.text.toString())
            dismiss()
        }
    }

}
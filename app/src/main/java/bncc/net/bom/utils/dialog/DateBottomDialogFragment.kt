package bncc.net.bom.utils.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import bncc.net.bom.R
import bncc.net.bom.ui.booking.BookingTicketActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DateBottomDialogFragment(var dummySeatLetter:Array<String>, var dummySeatNumber:Array<String>):BottomSheetDialogFragment() {

    private var selectedLetter:String ?= "A"
    private var selectedNumber:String ?= "1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.two_bottom_dialog, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<NumberPicker>(R.id.np_two1)?.maxValue = dummySeatLetter.size - 1
        view.findViewById<NumberPicker>(R.id.np_two1)?.minValue = 0
        view.findViewById<NumberPicker>(R.id.np_two1)?.displayedValues = dummySeatLetter

        view.findViewById<NumberPicker>(R.id.np_two2)?.maxValue = dummySeatNumber.size - 1
        view.findViewById<NumberPicker>(R.id.np_two2)?.minValue = 0
        view.findViewById<NumberPicker>(R.id.np_two2)?.displayedValues = dummySeatNumber

        view.findViewById<NumberPicker>(R.id.np_two1)?.setOnValueChangedListener { picker, oldValue, newValue ->
            selectedLetter = dummySeatLetter[newValue]
        }

        view.findViewById<NumberPicker>(R.id.np_two2)?.setOnValueChangedListener { picker, oldValue, newValue ->
            selectedNumber = dummySeatNumber[newValue]
        }

        view.findViewById<Button>(R.id.btn_submit_seat)?.setOnClickListener {
            (activity as BookingTicketActivity).addSeat("" + selectedLetter + selectedNumber)
            selectedLetter = "A"
            selectedNumber = "1"
            dismiss()
        }
    }

}
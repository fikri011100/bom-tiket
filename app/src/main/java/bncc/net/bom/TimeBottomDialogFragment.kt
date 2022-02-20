package bncc.net.bom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimeBottomDialogFragment(var dummyList:Array<String>):BottomSheetDialogFragment() {

    private var selected:String ?= "11:20"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.one_bottom_dialog, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<NumberPicker>(R.id.np_one)?.maxValue = dummyList.size - 1
        view.findViewById<NumberPicker>(R.id.np_one)?.minValue = 0
        view.findViewById<NumberPicker>(R.id.np_one)?.displayedValues = dummyList

        view.findViewById<Button>(R.id.btn_choose_time)?.setOnClickListener {
            (activity as BookingTicketActivity).setTime(selected.toString())
            dismiss()
        }

        view.findViewById<NumberPicker>(R.id.np_one)?.setOnValueChangedListener { picker, oldValue, newValue ->
            selected = dummyList[newValue]
        }

    }
}
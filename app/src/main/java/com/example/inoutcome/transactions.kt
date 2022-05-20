package com.example.inoutcome

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inoutcome.MainActivity.Companion.arrAny
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_transactions.*

import android.widget.RadioButton
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [transactions.newInstance] factory method to
 * create an instance of this fragment.
 */
class transactions : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var fillKat: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment transactions.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            transactions().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillKat = "All"
        println("Cek + " + fillKat)

        rcvtrans.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterHalfScreen(arrAny)
        }

        editTextDate.setOnClickListener{
            val today = Calendar.getInstance()
            val mYear = today[Calendar.YEAR]
            val mMonth = today[Calendar.MONTH]
            val mDay = today[Calendar.DAY_OF_MONTH]
            val mHour = today[Calendar.HOUR]
            val mMinute = today[Calendar.MINUTE]

            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    fun(datePicker: DatePicker?, i: Int, i1: Int, i2: Int) {
                        println("CEK = " + Integer.toString(i2) + "-" + Integer.toString(i1 + 1) + "-" + Integer.toString(i))
                        editTextDate.setText(Integer.toString(i2) + "-" + Integer.toString(i1 + 1) + "-" + Integer.toString(i))
                    },
                    mYear, mMonth, mDay
                )
            }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }

        btnref.setOnClickListener {
            editTextDate.setText("'dd-MM-yyyy'")
        }

        imgsrc.setOnClickListener {

            var tempdt: ArrayList<data> = returnspintype(arrAny)
            tempdt = returnKat(tempdt)
            tempdt = returndate(tempdt)

            rcvtrans.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = adapterHalfScreen(tempdt)
            }
        }


        radGrup.setOnCheckedChangeListener { radioGroup, i ->
            val rb = requireView().findViewById(i) as RadioButton
            fillKat = rb.text.toString()
            println("Cek " + rb.text.toString())
        }

        fillcat.setOnClickListener {
            if(newcateg.text.toString() != ""){
                val rb_flash: RadioButton = RadioButton(context)
                rb_flash.setText(newcateg.text.toString().lowercase().capitalize())
                rb_flash.setTextColor(Color.BLACK)
                rb_flash.id
                if(radGrup != null){
                    radGrup.addView(rb_flash)
                }
            }
            newcateg.setText("")
        }
    }

    fun returnspintype(arr: ArrayList<data>): ArrayList<data> {
        var arrTemp = arrayListOf<data>()
        if(spintype.selectedItem.toString() == "All"){
            arrTemp = arr
        }
        else if(spintype.selectedItem.toString() == "Income"){
            for(i in 0 until arr.size){
                if(arr.get(i).tipe == "Income"){
                    arrTemp.add(arr.get(i))
                }
            }
        }
        else if(spintype.selectedItem.toString() == "Outcome"){
            for(i in 0 until arr.size){
                if(arr.get(i).tipe == "Outcome"){
                    arrTemp.add(arr.get(i))
                }
            }
        }
        return arrTemp
    }
    fun returnKat(arr: ArrayList<data>): ArrayList<data>{
        var arrTemp = arrayListOf<data>()
        if(fillKat == "All"){
            arrTemp = arr
        }
        else
        {
            for(i in 0 until arr.size){
                if (arr.get(i).kategori == fillKat){
                    arrTemp.add(arr.get(i))
                }
            }
        }
        return  arrTemp
    }
    fun returndate(arr: ArrayList<data>): ArrayList<data>{
        var arrTemp = arrayListOf<data>()
        if(editTextDate.text.toString() == "'dd-MM-yyyy'"){
            arrTemp = arr
        }
        else
        {
            for(i in 0 until arr.size){
                if (arr.get(i).tanggal == editTextDate.text.toString()){
                    arrTemp.add(arr.get(i))
                }
            }
        }
        return arrTemp
    }
}
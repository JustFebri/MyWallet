package com.example.inoutcome

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat

import android.widget.DatePicker
import com.example.inoutcome.MainActivity.Companion.arrAny
import com.example.inoutcome.MainActivity.Companion.income
import com.example.inoutcome.MainActivity.Companion.outcome
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [add.newInstance] factory method to
 * create an instance of this fragment.
 */
class add : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var mDatabase: DatabaseReference? = null
    var mUserDatabase: DatabaseReference? = null
    var ArrayUser: ArrayList<data> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().getReference("dbuas")
        mUserDatabase = mDatabase!!.child("dbInOutCome")

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
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment add.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            add().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        var formattedDate = df.format(c)
        datenow.setText(formattedDate)

        btnsetdate.setOnClickListener {
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
                        datenow.setText(Integer.toString(i2) + "-" + Integer.toString(i1 + 1) + "-" + Integer.toString(i))
                    },
                    mYear, mMonth, mDay
                )
            }
            if (datePickerDialog != null) {
                datePickerDialog.show()
            }
        }

        btnconfirm.setOnClickListener {
            if(idcur.text.toString() != "0" && edtcat.text.toString() != "")
            {
                var dat = data(idcur.text.toString().toInt(), spinnertipe.selectedItem.toString(), datenow.text.toString(), edtcat.text.toString().lowercase().capitalize())
                println("Data = " + dat)
                insertfirebase(dat)
                arrAny.clear()
                selectfirebase()
                Toast.makeText(context, "Transaction saved", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Some parameter is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun insertfirebase(kirim: data) {
        val user = kirim
        val hsl = "abcdefghijklmnopqrstuvwxyz0123456789"
        var t = mDatabase!!.push().key
        user.key = t
        if (t != null) {
            mUserDatabase!!.child(t).setValue(user)
        }
    }

    fun selectfirebase() {
        // Read from the database
        ArrayUser = ArrayList()
        mUserDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (noteSnapshot in dataSnapshot.children) {
                    val note = noteSnapshot.getValue(
                        data::class.java
                    )
                    if (note != null) {
                        ArrayUser.add(note)
                    }
                    showdata()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println(databaseError.message)
            }
        })
    }

    fun showdata(){
        arrAny.clear()
        income = 0
        outcome = 0
        for (i in 0 until ArrayUser.size) {
            arrAny.add(ArrayUser.get(i))

            if(arrAny.get(i).tipe == "Income"){
                income = income + arrAny.get(i).currency!!.toInt()
            }
            else if(arrAny.get(i).tipe == "Outcome")
            {
                outcome  = outcome + arrAny.get(i).currency!!.toInt()
            }
        }
    }
}
package com.example.inoutcome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inoutcome.MainActivity.Companion.ArrayUser
import com.example.inoutcome.MainActivity.Companion.arrAny
import com.example.inoutcome.MainActivity.Companion.income
import com.example.inoutcome.MainActivity.Companion.outcome
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home.newInstance] factory method to
 * create an instance of this fragment.
 */
class home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    var mDatabase: DatabaseReference? = null
    var mUserDatabase: DatabaseReference? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txttot.setText((income- outcome).toString())
        txtin.setText(income.toString())
        txtout.setText(outcome.toString())

        mDatabase = FirebaseDatabase.getInstance().getReference("dbuas")
        mUserDatabase = mDatabase!!.child("dbInOutCome")

        selectfirebase()
    }

    fun selectfirebase() {
        // Read from the database
        ArrayUser.clear()
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
                }
                showdata()
                show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println(databaseError.message)
            }
        })
    }

    fun show(){
        if(rcv != null) {
            rcv.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = adapterdata(arrAny)
            }
        }
    }

    fun showdata(){
        arrAny.clear()
        income = 0
        outcome = 0
        for (i in 0 until MainActivity.ArrayUser.size) {
            arrAny.add(MainActivity.ArrayUser.get(i))

            if(arrAny.get(i).tipe == "Income"){
                income = income + arrAny.get(i).currency!!.toInt()
            }
            else if(arrAny.get(i).tipe == "Outcome")
            {
                outcome  = outcome + arrAny.get(i).currency!!.toInt()
            }
        }
        if(txtout != null && txtin != null && txttot != null){
            txttot.setText((income- outcome).toString())
            txtin.setText(income.toString())
            txtout.setText(outcome.toString())
        }

    }

}
package com.example.inoutcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add.*

class MainActivity : AppCompatActivity() {
    companion object{
        var ArrayUser: ArrayList<data> = ArrayList()
        var arrAny = arrayListOf<data>()
        var income: Int = 0
        var outcome: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentManager = supportFragmentManager
        val mhome = home()

        mFragmentManager.findFragmentByTag(home::class.java.simpleName)
        mFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, mhome, home::class.java.simpleName)
            .commit()

        btmnavbar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.page_1 -> {
                    val mFragmentManager = supportFragmentManager
                    val mhome = home()
                    mFragmentManager.findFragmentByTag(home::class.java.simpleName)
                    mFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainer, mhome, home::class.java.simpleName)
                        .commit()
                    true
                }
                R.id.page_2 -> {
                    val mFragmentManager = supportFragmentManager
                    val madd = add()

                    mFragmentManager.findFragmentByTag(add::class.java.simpleName)
                    mFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainer, madd, add::class.java.simpleName)
                        .commit()
                    true
                }
                R.id.page_3 -> {
                    val mFragmentManager = supportFragmentManager
                    val mtransactions = transactions()

                    mFragmentManager.findFragmentByTag(transactions::class.java.simpleName)
                    mFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameContainer, mtransactions, transactions::class.java.simpleName)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }


}
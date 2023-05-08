package com.example.assignment_day06.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.assignment_day06.R
import com.example.assignment_day06.ui.fragment.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag","MainActivity onCreate")
        setContentView(R.layout.activity_main)
        val listFragment = ListFragment()
        openFragment(ListFragment(),false,null)
    }
    fun openFragment(
        fragment : Fragment,
        isAdd : Boolean,
        bundle : Bundle?
    ){
        supportFragmentManager.commit {
            if(isAdd)   add(R.id.fcvFragment,fragment::class.java,bundle)
            else{
                replace(R.id.fcvFragment,fragment::class.java,bundle)
                addToBackStack(null)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("tag","MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("tag","MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("tag","MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("tag","MainActivity onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("tag","MainActivity onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("tag","MainActivity onDestroy")
    }
}
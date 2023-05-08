package gst.trainingcourse.fragmentdemo

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.widget.StackView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.internal.ContextUtils.getActivity
import gst.trainingcourse.fragmentdemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_blank.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSend.setOnClickListener{
            val fragment = BlankFragment()
            val bundle = Bundle()
            bundle.putString("string",etText.text.toString())
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,fragment)
                addToBackStack("Fragment A")
                commit()
            }
        }
        binding.btnChange.setOnClickListener{
            val nameFragment = NameFragment()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,nameFragment)
                setReorderingAllowed(true)
                addToBackStack("Fragment B")
                commit()
            }
        }
    }
}
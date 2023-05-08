package com.example.assignment_day09

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment_day09.databinding.ActivityMainBinding
import com.example.myapplication.Contact
import com.example.myapplication.ContactAdapter

class MainActivity : AppCompatActivity() {
    val READ_CONTACTS = 101
    private lateinit var binding : ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("tag","onCreate")
        checkForPermission(Manifest.permission.READ_CONTACTS,"contacs",READ_CONTACTS)
        binding.btnAddContact.setOnClickListener{
            val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, "")
            }
            startActivity(intent)

        }
    }
    fun checkForPermission(permission: String,name:String,requestCode : Int){
        Log.d("tag","checkforPermission")
        when{
            ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED -> {
                getContactFromDevice()
            }
            shouldShowRequestPermissionRationale(permission) ->showDialog(permission,name,requestCode)
            else -> ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("tag","onRequest")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission refused", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Permission granted", Toast.LENGTH_SHORT).show()
                getContactFromDevice()
            }
        }
        when(requestCode){
            READ_CONTACTS -> innerCheck("contacts")
        }
    }
    @SuppressLint("Range")
    private fun getContactFromDevice(){
        Log.d("tag","getContactFromDevice")
        val contactList: MutableList<Contact> = ArrayList()
        val contact = this.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME )
        if (contact != null){
            while (contact.moveToNext()){
                val id = contact.getString(
                    contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID)
                )
                val name = contact.getString(
                    contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                )
                val number = contact.getString(
                    contact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                )
                contactList.add(Contact(id,name, number))
            }

            contact.close()
        }
        contactAdapter = ContactAdapter(contactList)
        binding.rvContacts.adapter = contactAdapter
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
    }
    private fun showDialog(permission: String, name: String, requestCode: Int){
        val builder  = AlertDialog.Builder(this).apply {
            setMessage("Read Contact isn't allowed")
            setTitle("Permission required")
            setPositiveButton("OK"){dialog,which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        Log.d("tag","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("tag","onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d("tag","onResume")
        getContactFromDevice()
    }
}
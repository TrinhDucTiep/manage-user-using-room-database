package com.example.roomdatabasetutorial

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.roomdatabasetutorial.database.UserDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var edtUsername: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdateUser: Button

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        edtUsername = findViewById(R.id.edt_username)
        edtAddress = findViewById(R.id.edt_address)
        btnUpdateUser = findViewById(R.id.btn_update_user)

        user = intent.extras?.get("object_user") as User
        if(user != null){
            edtUsername.setText(user.username)
            edtAddress.setText(user.address)
        }

        btnUpdateUser.setOnClickListener{
            updateUser()
        }
    }

    private fun updateUser(){
        var strUserName: String = edtUsername.text.toString().trim()
        var strAddress: String = edtAddress.text.toString().trim()

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress)){
            return
        }

        //update user in db
        user.username = strUserName
        user.address = strAddress
        UserDatabase.getInstance(this).userDao().updateUser(user)
        Toast.makeText(this, "Update user successfully", Toast.LENGTH_SHORT).show()

        var intentResult = Intent()
        setResult(Activity.RESULT_OK, intentResult)
        finish()
    }
}
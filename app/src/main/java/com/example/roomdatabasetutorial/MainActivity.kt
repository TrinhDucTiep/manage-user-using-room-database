package com.example.roomdatabasetutorial

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasetutorial.database.UserDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var edtUserName: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnAddUser: Button
    private lateinit var rcvUser: RecyclerView

    private lateinit var userAdapter: UserAdapter
    private lateinit var mListUser: List<User>

    companion object{
        var MY_REQUEST_CODE: Int = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        userAdapter = UserAdapter(iClickListener = {
            clickUpdateUser(it)
        },
        iDeleteListener = {
            clickDeleteUser(it)
        })

        mListUser = ArrayList<User>()
        userAdapter.setData(mListUser)

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)

        rcvUser.layoutManager = linearLayoutManager
        rcvUser.adapter = userAdapter

        btnAddUser.setOnClickListener{
            addUser()
        }

        loadData()
    }

    private fun init(){
        edtUserName = findViewById(R.id.edt_username)
        edtAddress = findViewById(R.id.edt_address)
        btnAddUser = findViewById(R.id.btn_add_user)
        rcvUser = findViewById(R.id.rcv_user)
    }

    private fun addUser() {
        var strUserName: String = edtUserName.text.toString().trim()
        var strAddress: String = edtAddress.text.toString().trim()

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress)){
            return
        }

        var user: User = User(strUserName, strAddress)

        if(isUserExist(user)){
            Toast.makeText(this, "User existed", Toast.LENGTH_SHORT).show()
            return
        }

        UserDatabase.getInstance(this).userDao().insertUser(user)
        Toast.makeText(this, "Add user successfully", Toast.LENGTH_SHORT).show()

        edtUserName.setText("")
        edtAddress.setText("")

        hideSoftKeyboead()

        loadData()
    }

    public fun hideSoftKeyboead(){
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun loadData(){
        mListUser = UserDatabase.getInstance(this).userDao().getListUser()
        userAdapter.setData(mListUser)
    }

    public fun isUserExist(user: User): Boolean{
        val list: List<User> ? = UserDatabase.getInstance(this).userDao().checkUser(user.username)
        return (list != null) && (!list.isEmpty())
    }

    private fun clickUpdateUser(user: User){
        var intent = Intent(this, UpdateActivity::class.java)
        var bundle: Bundle = Bundle()
        bundle.putSerializable("object_user", user)
        intent.putExtras(bundle)
        startActivityForResult(intent, MY_REQUEST_CODE)
//        startActivity(intent)
    }

    private fun clickDeleteUser(user: User){
        UserDatabase.getInstance(this).userDao().deleteUser(user)
        Toast.makeText(this, "Delete user successfully", Toast.LENGTH_SHORT).show()

        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == MY_REQUEST_CODE && resultCode==Activity.RESULT_OK){
            loadData()
        }
    }
}
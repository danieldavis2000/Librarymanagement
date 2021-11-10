package com.dapp.librarymanagement.Activity.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.Database.UserEntity


import com.dapp.librarymanagement.R

class RegisterActivity : AppCompatActivity() {
    lateinit var spref : SharedPreferences
    lateinit var etname: EditText
    lateinit var etphno: EditText
    lateinit var etemail: EditText
    lateinit var etusername:EditText
    lateinit var etpass: EditText
    lateinit var etconfirmpass: EditText
    lateinit var btnregister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Register Yourself"
        etusername = findViewById(R.id.etusernameregister)
        etname = findViewById(R.id.etnameregister)
        etphno = findViewById(R.id.etphnoregister)
        etemail = findViewById(R.id.etemailidregister)
        etpass = findViewById(R.id.etpassregister)
        etconfirmpass = findViewById(R.id.etconfirmpass)
        btnregister = findViewById(R.id.btnRegisterdisplay)
        spref = getSharedPreferences("Loginflag", Context.MODE_PRIVATE)
        btnregister.setOnClickListener {

            var flag = true
            if (!(etpass.text.toString().equals(etconfirmpass.text.toString()))) {
                flag = false
                Toast.makeText(
                    this@RegisterActivity,
                    " Password doesn't match!!! ",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (etname.text.length <= 3) {
                flag = false
                Toast.makeText(
                    this@RegisterActivity,
                    " Invalid name ",
                    Toast.LENGTH_LONG
                ).show()
            }
            if ("gmail.com" !in etemail.text.toString() ) {
                flag = false
                Toast.makeText(
                    this@RegisterActivity,
                    " Invalid email id ",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (etphno.text.length == 9) {
                Toast.makeText(
                    this@RegisterActivity,
                    " Invalid Phone number ",
                    Toast.LENGTH_LONG
                ).show()
                flag = false
            }
            if (UserDetailsAsyncTask(this@RegisterActivity,etusername.text.toString()).execute().get()) {
                Toast.makeText(
                    this@RegisterActivity,
                    " User name already exists ",
                    Toast.LENGTH_LONG
                ).show()
                flag = false
            }

            if (flag) {
                print("in flag")
//
                spref.edit().putBoolean("isloggedin",true).apply()
                val user = UserEntity(etusername.text.toString(),etname.text.toString(),etemail.text.toString(),etphno.text.toString(),etpass.text.toString())
                if(UserInputAsyncTask(this@RegisterActivity,user).execute().get()){

                val intent =Intent(this@RegisterActivity,MainActivity :: class.java)
                intent.putExtra("userid",etusername.text.toString())
                spref.edit().putString("username",etusername.text.toString()).apply()
                startActivity(intent)
                finish()
                }
            }

        }
    }
//    override fun onPause() {
//        super.onPause()
//        finish()
//    }
    class UserDetailsAsyncTask(context: Context, val usercredential: String) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {


            return db.userdao().getuserById(usercredential)!=null
        }

    }
    class UserInputAsyncTask(context: Context, val userentity: UserEntity) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {
            db.userdao().insertuser(userentity)
            db.close()
            return true
        }

    }


    }
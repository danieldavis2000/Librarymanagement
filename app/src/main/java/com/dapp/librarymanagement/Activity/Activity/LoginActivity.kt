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
var globalid = 1
class LoginActivity : AppCompatActivity() {
    lateinit var etusername: EditText
    lateinit var etpassword: EditText
    lateinit var loginbtn: Button
    lateinit var btnforpass: Button
    lateinit var Registerbtn: Button
    lateinit var loginflag : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etusername = findViewById(R.id.etusername)
        etpassword = findViewById(R.id.etpass)
        loginflag=getSharedPreferences("Loginflag", Context.MODE_PRIVATE)
        loginbtn = findViewById(R.id.btnlogin)
        btnforpass = findViewById(R.id.btnforgotpass)
        Registerbtn = findViewById(R.id.btnRegister)

        if(loginflag.getBoolean("isloggedin",false))
        {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginbtn.setOnClickListener {
            var uname = etusername.text.toString()
            var Pass = etpassword.text.toString()
            var flag = true
            if (Pass.length <= 3) {
                flag = false
                Toast.makeText(
                    this@LoginActivity,
                    "Invalid Password ",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (uname.length < 4) {
                Toast.makeText(
                    this@LoginActivity,
                    " Invalid username ",
                    Toast.LENGTH_LONG
                ).show()
                flag = false
            }
            if (flag) {
                if((uname == "group9@library") && (Pass == "1234")){
                    loginflag.edit().putBoolean("isloggedin",true).apply()
                    loginflag.edit().putString("username",uname).apply()
                    val intent = Intent(this@LoginActivity,
                        MainActivity::class.java)
                    startActivity(intent)
                }
                val user = UserDetailsAsyncTask(this@LoginActivity,uname).execute().get()
                if(user!=null){
                    if(Pass == user.userpass){
                        loginflag.edit().putBoolean("isloggedin",true).apply()
                        loginflag.edit().putString("username",uname).apply()
                        val intent = Intent(this@LoginActivity,
                            MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                    else{
                        Toast.makeText(
                            this@LoginActivity,
                            "Wrong Password ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else{
                    Toast.makeText(
                        this@LoginActivity,
                        " Username  does not exist ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        Registerbtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    class UserDetailsAsyncTask(context: Context, val usercredential: String) :
        AsyncTask<Void, Void, UserEntity>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): UserEntity {


            return db.userdao().getuserById(usercredential)
        }

    }
}
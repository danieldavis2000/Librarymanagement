package com.dapp.librarymanagement.Activity.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.dapp.librarymanagement.Activity.Activity.MyBooks
import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.Database.UserEntity
import com.dapp.librarymanagement.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    lateinit var txtusername:TextView
    lateinit var name:TextView
    lateinit var email:TextView
    lateinit var phno:TextView
    lateinit var btnbooks : Button
    lateinit var loginflag : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        name = view.findViewById(R.id.txtprofilename)
        email =view.findViewById(R.id.txtemail)
        phno =view.findViewById(R.id.txtphno)
        txtusername=view.findViewById(R.id.txtusername)
        btnbooks = view.findViewById(R.id.btnmyboks)
        loginflag= activity!!.getSharedPreferences("Loginflag", Context.MODE_PRIVATE)
        val username = loginflag.getString("username",null)
        if(username!= null){
            val user = UserDetailsAsyncTask(activity as Context,username).execute().get()
            txtusername.text = user.userid
            name.text = user.username
            email.text = user.useremail
            phno.text = user.userphno
        }
        else{
            txtusername.text = "userid"
            name.text = "username"
            email.text = "email"
            phno.text ="phone number"
        }
        btnbooks.setOnClickListener {
            val intnt = Intent(context as Activity , MyBooks::class.java)
            startActivity(intnt)
        }
        return view
    }
    class UserDetailsAsyncTask(context: Context, val usercredential: String) :
        AsyncTask<Void, Void, UserEntity>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): UserEntity {


            return db.userdao().getuserById(usercredential)
        }

    }
}
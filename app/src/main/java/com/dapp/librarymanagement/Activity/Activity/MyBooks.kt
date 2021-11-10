package com.dapp.librarymanagement.Activity.Activity

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.dapp.librarymanagement.Activity.Adapter.DashboardRecyclerAdapter
import com.dapp.librarymanagement.Activity.Adapter.MyBooksAdapter
import com.dapp.librarymanagement.Activity.Database.BookEntity
import com.dapp.librarymanagement.Activity.Database.IssuesEntity
import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.Fragment.DashboardFragment
import com.dapp.librarymanagement.Activity.model.book
import com.dapp.librarymanagement.Activity.model.records
import com.dapp.librarymanagement.R

class MyBooks : AppCompatActivity() {

    lateinit var recyclermybooks: RecyclerView
    lateinit var layoutmanager: RecyclerView.LayoutManager
    lateinit var recycleradapter: MyBooksAdapter
    lateinit var loginflag:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_books)
        loginflag=getSharedPreferences("Loginflag", Context.MODE_PRIVATE)

        val userid = loginflag.getString("username",null)
        if(userid != null) {
            val allrecords = AllrecordsAsync(this@MyBooks, userid).execute().get()

            val recordlist = arrayListOf<records>()

            if (!(allrecords.isEmpty())) {
                for (i in allrecords) {
                    recordlist.add(
                        records(
                            i.userid,
                            i.bookid,
                            i.issuedate,
                            i.returndate,
                            i.userfine,
                            i.bookstatus
                        )
                    )
                }
            }
            recyclermybooks = findViewById(R.id.RecyclerMyBooks)
            layoutmanager = LinearLayoutManager(this@MyBooks)
            recycleradapter =
                MyBooksAdapter(this@MyBooks, recordlist)

            recyclermybooks.adapter = recycleradapter
            recyclermybooks.layoutManager = layoutmanager
            recyclermybooks.addItemDecoration(
                DividerItemDecoration(
                    recyclermybooks.context,
                    (layoutmanager as LinearLayoutManager).orientation
                )
            )
        }
    }
    class AllrecordsAsync(context: Context,val userid : String) : AsyncTask<Void, Void, List<IssuesEntity>>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<IssuesEntity> {
            db.close()
            return db.IssuesDao().getAllBooksByUserId(userid)

        }

    }
}
package com.dapp.librarymanagement.Activity.Activity

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.room.Room

import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.Database.BookEntity
import com.dapp.librarymanagement.Activity.Database.IssuesEntity
import com.dapp.librarymanagement.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_descripton.*

class DescriptonActivity : AppCompatActivity() {

    lateinit var descbookname : TextView
    lateinit var descbookauthor : TextView
    lateinit var descbookcode : TextView
    lateinit var descbookgenre : TextView
    lateinit var descbookdescription : TextView
    lateinit var descimgbookimage : ImageView
    lateinit var descbtnreqbook : Button
    lateinit var book :BookEntity
    lateinit var loginflag : SharedPreferences
    var bookId :String? = "100"
    lateinit var desctoolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripton)
         descbookname = findViewById(R.id.txtdescBookName)
        descbookauthor = findViewById(R.id.txtdescBookAuthor)
        descbookcode = findViewById(R.id.txtdescBookCode)
        descbookgenre = findViewById(R.id.txtdescBookGenre)
        descbookdescription = findViewById(R.id.txtbookdescription)
        descimgbookimage = findViewById(R.id.imgdescBookImage)
        descbtnreqbook = findViewById(R.id.btnrequestbook)
        loginflag=getSharedPreferences("Loginflag", Context.MODE_PRIVATE)


//        desctoolbar = findViewById(R.id.desctoolbar)
//        setSupportActionBar(desctoolbar)
//        supportActionBar?.title = "Book Details"




        if (intent != null) {
            bookId = intent.getStringExtra("bookid")
            book = bookAsync(this@DescriptonActivity,bookId).execute().get()
            descbookname.text = book.bookname
            descbookauthor.text = book.bookauthor
            descbookcode.text = book.bookid
            descbookgenre.text = book.bookgenre
            descbookdescription.text=book.bookdesc
            Picasso.get().load(R.drawable.book_app_icon_web).error(R.drawable.book_app_icon_web).into(descimgbookimage)
            val userid = loginflag.getString("username",null)
            if(userid != null) {

                var issue: IssuesEntity = IssuesEntity(
                    userid.toString(),
                    book.bookid,
                    "today",
                    "TODAY",
                    "0",
                    "Request"
                )
                if (DBAsyncTask(this@DescriptonActivity, issue, 1).execute().get()) {
                    btnrequestbook.text = "Cancel Request"
                } else {
                    btnrequestbook.text = "Request Book"
                }


                descbtnreqbook.setOnClickListener {
                    Toast.makeText(this@DescriptonActivity, "clicked", Toast.LENGTH_SHORT).show()
                    if (DBAsyncTask(this@DescriptonActivity, issue, 1).execute().get()) {
                        btnrequestbook.text = "Request Book"

                        DBAsyncTask(this@DescriptonActivity, issue, 3).execute().get()
                    } else {
                        btnrequestbook.text = "Cancel Request"

                        DBAsyncTask(this@DescriptonActivity, issue, 2).execute().get()
                    }

                }
            }
            else
            {
                Toast.makeText(this@DescriptonActivity, " no user ", Toast.LENGTH_SHORT).show()
            }
        }
        else {

            Toast.makeText(
                this@DescriptonActivity,
                " Some unexpected error occured",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        if (bookId == "100") {

            Toast.makeText(
                this@DescriptonActivity,
                " Some unexpected error occured 100",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }



    }
    class DBAsyncTask(val context :Context,val issuesentity : IssuesEntity, val mode : Int): AsyncTask< Void , Void , Boolean>() {

        val db = Room.databaseBuilder(context,LibraryDatabase::class.java,"res-db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {
                when(mode){
                    1 -> {

                        val book : IssuesEntity = db.IssuesDao().getRecordById(issuesentity.bookid)
                        db.close()
                        return if(book!=null) {
                            book != null
                        } else{
                            false
                        }
                    }
                    2 ->{
                        db.IssuesDao().insertrecord(issuesentity)
                        db.close()
                        return true
                    }
                    3 -> {
                        db.IssuesDao().deleterecord(issuesentity)
                        db.close()
                        return true
                    }
                }
            return false
        }

    }
    class bookAsync(context: Context, val bookid :String?) : AsyncTask<Void, Void, BookEntity>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): BookEntity {

            return db.bookdao().getBookById(bookid)
            db.close()
        }

    }
}
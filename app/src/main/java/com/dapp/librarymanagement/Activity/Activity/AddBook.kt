package com.dapp.librarymanagement.Activity.Activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.dapp.librarymanagement.Activity.Database.BookEntity
import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.R

class AddBook : AppCompatActivity() {
    lateinit var etbookid:EditText
    lateinit var etbookname : EditText
    lateinit var etbookgenre : EditText
    lateinit var etbookauthor : EditText
    lateinit var etbookdesc : EditText
    lateinit var addbookbtn :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)
        etbookid = findViewById(R.id.etbookid)
        etbookname = findViewById(R.id.etbookname)
        etbookauthor = findViewById(R.id.etbookauthor)
        etbookgenre = findViewById(R.id.etbookgenre)
        etbookdesc = findViewById(R.id.etbookdesc)
        addbookbtn = findViewById(R.id.btnaddbook)
        addbookbtn.setOnClickListener {
            if(etbookid.text.toString() =="" || (etbookname.text.toString() == "")|| (etbookgenre.text.toString() == "") || (etbookauthor.text.toString() == "") || (etbookdesc.text.toString() == "")){
            Toast.makeText(this@AddBook, "Columns should not be empty", Toast.LENGTH_SHORT).show()
        }
            else{
              val bookentity = BookEntity(etbookid.text.toString(),etbookname.text.toString(),etbookauthor.text.toString(),etbookgenre.text.toString(),R.drawable.book_app_icon_web.toString(),etbookdesc.text.toString())
                val flag = InsertbooksAsync(this@AddBook,bookentity).execute().get()
                if(flag) {
                    Toast.makeText(this@AddBook, "Book Added", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    class InsertbooksAsync(context: Context ,val bookEntity: BookEntity ) : AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            db.bookdao().insertBook(bookEntity)
            db.close()
            return true
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val refresh = Intent(this,MainActivity::class.java)
        startActivity(refresh)
        this.finish()
    }
}
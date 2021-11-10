package com.dapp.librarymanagement.Activity.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.dapp.librarymanagement.Activity.Activity.DescriptonActivity
import com.dapp.librarymanagement.Activity.Database.BookEntity
import com.dapp.librarymanagement.Activity.Database.IssuesEntity
import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.model.records
import com.dapp.librarymanagement.R
import com.squareup.picasso.Picasso

class MyBooksAdapter(val context : Context , val itemlist : ArrayList<records>) : RecyclerView.Adapter<MyBooksAdapter.BooksViewHolder> ()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mybooksingleview,parent,false)
        return   BooksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val records = itemlist[position]
        holder.txtbookname.text = bookAsync(context as Activity,records.bookid).execute().get().toString()
        holder.txtbookid.text = records.bookid
        holder.txtbookstatus.text = records.status
        holder.dashboardview.setOnClickListener {
            val intent = Intent(context, DescriptonActivity::class.java)
            intent.putExtra("bookid",records.bookid)
            context.startActivity(intent)
        }
    }
    class BooksViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtbookname : TextView = view.findViewById(R.id.mybookname)
        val txtbookid : TextView = view.findViewById(R.id.mybookid)
        val txtbookstatus : TextView = view.findViewById(R.id.mybookstatus)
        val dashboardview : LinearLayout = view.findViewById(R.id.layoutmybooks)
    }

    class bookAsync(context: Context,val bookid : String) : AsyncTask<Void, Void,String> () {

        val db = Room.databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): String {

            val book = db.bookdao().getBookById(bookid)
            db.close()
            return book.bookname
        }

    }
}
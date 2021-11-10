package com.dapp.librarymanagement.Activity.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.dapp.librarymanagement.Activity.Database.BookEntity
import com.dapp.librarymanagement.Activity.Activity.DescriptonActivity
import com.dapp.librarymanagement.R
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context ,val booklist : List <BookEntity>) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboardsingleview,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return booklist.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book= booklist[position]
        holder.txtbookname.text = book.bookname
        holder.txtbookauthor.text = book.bookauthor
        holder.txtbookprice.text = book.bookid
        holder.txtbookrating.text = book.bookgenre
        Picasso.get().load(R.drawable.default_book_cover).error(R.drawable.default_book_cover).into(holder.imgbookimage)
        holder.dashboardview.setOnClickListener {
            val intent = Intent(context, DescriptonActivity::class.java)
            intent.putExtra("bookid",book.bookid)
            context.startActivity(intent)
        }
    }
    class FavouriteViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtbookname : TextView = view.findViewById(R.id.txtBookName)
        val txtbookauthor : TextView = view.findViewById(R.id.txtBookAuthor)
        val txtbookprice : TextView = view.findViewById(R.id.txtBookPrice)
        val txtbookrating : TextView = view.findViewById(R.id.txtBookRating)
        val imgbookimage : ImageView = view.findViewById(R.id.imgBookImage)
        val dashboardview : LinearLayout = view.findViewById(R.id.dashboardsingleview)

    }
}
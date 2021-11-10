package com.dapp.librarymanagement.Activity.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dapp.librarymanagement.Activity.Activity.DescriptonActivity
import com.dapp.librarymanagement.Activity.model.book
import com.dapp.librarymanagement.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dashboardsingleview.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardRecyclerAdapter (val context : Context,val itemList : ArrayList<book>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboardsingleview,parent,false)
        return   DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtbookname.text = book.bookname
        holder.txtbookauthor.text = book.bookauthor
        holder.txtbookid.text = book.bookid
        holder.txtbookgenre.text = book.bookgenre
        Picasso.get().load(R.drawable.book_app_icon_web).error(R.drawable.book_app_icon_web).into(holder.imgbookimage)
        holder.dashboardview.setOnClickListener {
            val intent = Intent(context, DescriptonActivity::class.java)
            intent.putExtra("bookid",book.bookid)
            context.startActivity(intent)
        }
    }
    class DashboardViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtbookname : TextView = view.findViewById(R.id.txtBookName)
        val txtbookauthor : TextView = view.findViewById(R.id.txtBookAuthor)
        val txtbookid : TextView = view.findViewById(R.id.txtBookPrice)
        val txtbookgenre : TextView = view.findViewById(R.id.txtBookRating)
        val imgbookimage : ImageView = view.findViewById(R.id.imgBookImage)
        val dashboardview : LinearLayout = view.findViewById(R.id.dashboardsingleview)
    }
}
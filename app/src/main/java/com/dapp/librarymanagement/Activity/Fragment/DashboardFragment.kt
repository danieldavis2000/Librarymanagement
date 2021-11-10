package com.dapp.librarymanagement.Activity.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room.databaseBuilder
import com.dapp.librarymanagement.Activity.Activity.AddBook
import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.Database.BookEntity

import com.dapp.librarymanagement.Activity.Adapter.DashboardRecyclerAdapter
import com.dapp.librarymanagement.Activity.model.book
import com.dapp.librarymanagement.R
import java.util.*


import kotlin.Comparator


class DashboardFragment : Fragment() {


    lateinit var recyclerdashboard: RecyclerView
    lateinit var layoutmanager: RecyclerView.LayoutManager
    lateinit var recycleradapter: DashboardRecyclerAdapter
    lateinit var loginflag : SharedPreferences

    val ratingcomparotor = Comparator<book>{ book1,book2 ->
        if(book1.bookname.compareTo(book2.bookname) == 0){
            book1.bookname.compareTo(book2.bookname)
        }
        else{
            book1.bookname.compareTo(book2.bookname)
        }
    }
   val bookInfoList = arrayListOf<book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragme
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        loginflag= activity!!.getSharedPreferences("Loginflag", Context.MODE_PRIVATE)

        InsertbooksAsync(activity as Context).execute().get()

        val booksList = AllbooksAsync(activity as Context).execute().get()
        if (!(booksList.isEmpty())) {
            for (i in booksList) {
                bookInfoList.add(
                    book(
                        i.bookid,
                        i.bookname,
                        i.bookauthor,
                        i.bookgenre,
                        i.bookimage
                    )
                )
            }
        }
                        recyclerdashboard = view.findViewById(R.id.RecyclerDashboard)
                        layoutmanager = LinearLayoutManager(activity)
                        recycleradapter =
                            DashboardRecyclerAdapter(activity as Context, bookInfoList)

                        recyclerdashboard.adapter = recycleradapter
                        recyclerdashboard.layoutManager = layoutmanager
                        recyclerdashboard.addItemDecoration(
                            DividerItemDecoration(
                                recyclerdashboard.context,
                                (layoutmanager as LinearLayoutManager).orientation
                            )
                        )



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.add,menu)
        inflater?.inflate(R.menu.menu_dashboard,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item?.itemId
        if(id == R.id.sort){
            Collections.sort(bookInfoList,ratingcomparotor)
            bookInfoList.reverse()
        }
        val username = loginflag.getString("username",null)

        if(id==R.id.add){
            if(username == "group9@library"){

                    val intent = Intent(context as Activity, AddBook::class.java)
                    startActivity(intent)


            }
            else{
                Toast.makeText(context, "Not an Admin", Toast.LENGTH_SHORT).show()
            }
        }
        recycleradapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
    class AllbooksAsync(context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {

        val db = databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<BookEntity> {

            return db.bookdao().getAllBooks()
            db.close()
        }

    }
    class InsertbooksAsync(context: Context) : AsyncTask<Void, Void, Boolean>() {

        val db = databaseBuilder(context, LibraryDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {


            val bookEntity1 = BookEntity("C03","War and Peace","Leo Tolstoy","History fiction",R.drawable.default_book_cover.toString(),"War and Peace broadly focuses on Napoleon’s invasion of Russia in 1812 and follows three of the most well-known characters in literature: Pierre Bezukhov, the illegitimate son of a count who is fighting for his inheritance and yearning for spiritual fulfillment; Prince Andrei Bolkonsky, who leaves his family behind to fight in the war against Napoleon; and Natasha Rostov, the beautiful young daughter of a nobleman who intrigues both men.")
            if(bookEntity1.bookid !in db.bookdao().getAllBookId()) {
                db.bookdao().insertBook(bookEntity1)
            }
            val bookEntity2 = BookEntity("B01","The Moonstone","Wilkie Collins","Mystery",R.drawable.default_book_cover.toString(),"The moonstone in the title refers to a brilliant but flawed gem seized by a British officer in India. He brought it back to England as a family heirloom - with a supposed curse placed upon it. The officer bequeathed the stone to his niece, Rachel Verinder, for her to inherit when she turns 18. The night of her 18th birthday, the Moonstone goes missing. Everyone connected with Rachel at her family estate in Yorkshire is under suspicion. It is up to the London detective, Sergeant Cuff, to solve the crime.")
            if (bookEntity2.bookid !in db.bookdao().getAllBookId())
            { db.bookdao().insertBook(bookEntity2)}
            db.close()
            return true
        }

    }
}
package com.dapp.librarymanagement.Activity.Fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

import com.dapp.librarymanagement.Activity.Database.LibraryDatabase
import com.dapp.librarymanagement.Activity.Database.BookEntity
import com.dapp.librarymanagement.Activity.Adapter.FavouriteRecyclerAdapter

import com.dapp.librarymanagement.R

lateinit var recyclerfavourite : RecyclerView
lateinit var layoutmanager: RecyclerView.LayoutManager
lateinit var recycleradapter: FavouriteRecyclerAdapter
lateinit var progresslayout : RelativeLayout
lateinit var progressbar : ProgressBar
 var dbbooklist = listOf <BookEntity>()


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavouritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        progresslayout = view.findViewById(R.id.favprogresslayout)
        progressbar = view.findViewById(R.id.favprogressbar)

        recyclerfavourite = view.findViewById(R.id.RecyclerFavourite)

        layoutmanager =GridLayoutManager(activity as Context ,2)
        dbbooklist = RetrieveFavourites(activity as Context).execute().get()

      if(activity != null) {
          progresslayout.visibility = View.GONE
          recycleradapter = FavouriteRecyclerAdapter(activity as Context, dbbooklist)
          recyclerfavourite.adapter = recycleradapter
          recyclerfavourite.layoutManager = layoutmanager
      }
        return view
    }


    class RetrieveFavourites(val context : Context) : AsyncTask<Void,Void,List<BookEntity>>() {
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db =  Room.databaseBuilder(context, LibraryDatabase::class.java,"books_db").build()
            return db.bookdao().getAllBooks()
        }
    }
}
package com.dapp.librarymanagement.Activity.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("Delete from books ")
    fun  deleteall()

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM books WHERE book_id=:bookId")
    fun getBookById (bookId: String?) : BookEntity

    @Query("SELECT book_id FROM books")
    fun getAllBookId () : List<String>


}

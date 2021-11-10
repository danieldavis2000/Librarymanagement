package com.dapp.librarymanagement.Activity.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey @ColumnInfo(name = "book_id") val bookid : String,
    @ColumnInfo(name ="book_name") val bookname : String,
    @ColumnInfo(name ="book_author")val bookauthor : String,
    @ColumnInfo(name ="book_genre") val bookgenre : String,
    @ColumnInfo(name ="book_image") val bookimage : String,
    @ColumnInfo(name ="book_desc") val bookdesc : String
)
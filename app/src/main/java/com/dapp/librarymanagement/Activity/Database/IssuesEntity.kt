package com.dapp.librarymanagement.Activity.Database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Issues")
data class IssuesEntity (
    @ColumnInfo (name = "user_id") val userid : String,
    @PrimaryKey @ColumnInfo(name ="book_id") val bookid : String,
    @ColumnInfo(name ="issuedate")val issuedate : String,
    @ColumnInfo(name ="returndate") val returndate : String,
    @ColumnInfo(name ="user_fine") val userfine : String,
    @ColumnInfo(name ="book_status") val bookstatus : String
)
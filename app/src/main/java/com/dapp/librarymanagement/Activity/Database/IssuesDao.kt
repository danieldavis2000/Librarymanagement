package com.dapp.librarymanagement.Activity.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IssuesDao {
    @Insert
    fun insertrecord(IssuesEntity: IssuesEntity)

    @Delete
    fun deleterecord(issuesEntity: IssuesEntity)

    @Query("SELECT * FROM Issues WHERE book_id=:issuebookId")
    fun getRecordById (issuebookId: String) : IssuesEntity

    @Query("SELECT * FROM Issues")
    fun getAllBooks(): List<IssuesEntity>
    @Query("SELECT * FROM Issues where user_id =:Userid")
    fun getAllBooksByUserId(Userid : String): List<IssuesEntity>
}
package com.dapp.librarymanagement.Activity.Database


import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities =[UserEntity :: class , BookEntity :: class, IssuesEntity :: class], version = 1)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun bookdao() : BookDao
    abstract fun userdao() : UserDao
    abstract fun IssuesDao() : IssuesDao
}

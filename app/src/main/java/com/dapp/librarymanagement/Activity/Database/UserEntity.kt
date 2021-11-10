package com.dapp.librarymanagement.Activity.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "users")
data class UserEntity (@PrimaryKey @ColumnInfo(name = "user_id") val userid : String,
                       @ColumnInfo(name ="user_name") val username : String,
                       @ColumnInfo(name ="user_email")val useremail : String,
                       @ColumnInfo(name ="user_phno") val userphno : String,
                       @ColumnInfo(name ="user_pass") val userpass : String)
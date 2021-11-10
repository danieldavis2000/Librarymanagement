package com.dapp.librarymanagement.Activity.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {
    @Insert
    fun insertuser(userEntity: UserEntity)

    @Delete
    fun deleteuser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE user_id=:userId")
    fun getuserById (userId : String) : UserEntity
}
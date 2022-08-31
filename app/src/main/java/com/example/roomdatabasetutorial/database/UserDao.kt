package com.example.roomdatabasetutorial.database

import androidx.room.*
import com.example.roomdatabasetutorial.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM user")
    fun getListUser() : List<User>

    @Query("SELECT * FROM user where username = :username")
    fun checkUser(username: String): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}
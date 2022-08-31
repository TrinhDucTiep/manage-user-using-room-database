package com.example.roomdatabasetutorial.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabasetutorial.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    companion object{
        private val DATABASE_NAME: String = "user.db"
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(context, UserDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
//        fun getInstance(context: Context): UserDatabase{
//            if(instance == null){
//                instance = Room.databaseBuilder(context, UserDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
//            }
//            return instance as UserDatabase
//        }
    }

    abstract fun userDao(): UserDao
}
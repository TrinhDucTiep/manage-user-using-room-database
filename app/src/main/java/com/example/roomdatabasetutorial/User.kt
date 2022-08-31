package com.example.roomdatabasetutorial

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
class User (var username: String, var address: String) : Serializable {
     @PrimaryKey(autoGenerate = true) var id: Int = 0
     get() = field
     set(value){
          field = value
     }
}
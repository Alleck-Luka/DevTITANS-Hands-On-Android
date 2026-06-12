package com.example.plaintext.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PasswordDao : BaseDao<Password> {

    @Query("""
        SELECT * FROM passwords
    """)
    abstract fun passwords(): Flow<List<Password>>

    @Query("""
        SELECT * FROM passwords WHERE name = :name
    """)
    abstract fun passwordByName(name: String): Password?

    @Query("""SELECT * FROM passwords WHERE id = :id""")
    abstract fun passwordById(id: Int): Password?

    @Query("""SELECT EXISTS(SELECT * FROM passwords)""")
    abstract fun hasPasswords(): Boolean
}
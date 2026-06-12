package com.example.plaintext.data.repository

import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.flowOf

class FakePasswordDBStore : PasswordDBStore {

    override fun getList() = flowOf(emptyList<Password>())

    override suspend fun add(password: Password): Long = 0

    override suspend fun update(password: Password) {}

    override fun get(id: Int): Password? = null

    override suspend fun save(passwordInfo: PasswordInfo) {}

    override suspend fun isEmpty() = flowOf(true)
}
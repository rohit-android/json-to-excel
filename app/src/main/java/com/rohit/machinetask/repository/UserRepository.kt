package com.rohit.machinetask.repository

import com.rohit.machinetask.database.User
import com.rohit.machinetask.database.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }
}

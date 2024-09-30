package com.rohit.machinetask.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rohit.machinetask.database.User
import com.rohit.machinetask.database.UserDatabase
import com.rohit.machinetask.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }

    fun login(email: String, password: String): LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val user = repository.login(email, password)
            loginResult.postValue(user != null)
        }
        return loginResult
    }
}

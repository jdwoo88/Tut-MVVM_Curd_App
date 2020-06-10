package com.jwoo.mvvmcurd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jwoo.mvvmcurd.dbs.SubscriberRepository

class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model class")
    }
}
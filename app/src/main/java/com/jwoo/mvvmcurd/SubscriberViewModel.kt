package com.jwoo.mvvmcurd

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jwoo.mvvmcurd.dbs.Subscriber
import com.jwoo.mvvmcurd.dbs.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveButton = MutableLiveData<String>()

    @Bindable
    val clearButton = MutableLiveData<String>()

    init {
        saveButton.value = "Save"
        clearButton.value = "Clear"
    }

    fun Save() {
        val name: String = inputName.value!!
        val email:String = inputEmail.value!!
        insert(Subscriber(0, name, email))

        inputName.value = null
        inputEmail.value = null
    }

    fun Clear() {
        deleteAll()
    }

    fun insert(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.insert(subscriber) }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.update(subscriber) }

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.delete(subscriber) }

    fun deleteAll(): Job = viewModelScope.launch { repository.deleteAll() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
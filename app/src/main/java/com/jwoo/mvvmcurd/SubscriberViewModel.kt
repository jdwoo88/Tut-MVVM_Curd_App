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
    private var isUpdateOrDelete : Boolean = false
    private lateinit var subscriberToDelete : Subscriber

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
        clearButton.value = "Clear Db"
    }

    fun Save() {


        if (isUpdateOrDelete){
            subscriberToDelete.name = inputName.value!!
            subscriberToDelete.email = inputEmail.value!!
            update(subscriberToDelete)
        }
        else{
            val name: String = inputName.value!!
            val email:String = inputEmail.value!!

            insert(Subscriber(0, name, email))

            inputName.value = null
            inputEmail.value = null
        }
    }

    fun Clear() {
        if (isUpdateOrDelete){
            delete(subscriberToDelete)
        }
        else {
            deleteAll()
        }
    }

    fun insert(subscriber: Subscriber): Job =
        viewModelScope.launch { repository.insert(subscriber) }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.update(subscriber)

            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false;
            saveButton.value = "Save"
            clearButton.value = "Clear Db"
        }

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.delete(subscriber)

            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false;
            saveButton.value = "Save"
            clearButton.value = "Clear Db"
        }

    fun deleteAll(): Job = viewModelScope.launch { repository.deleteAll() }

    fun initUpdateAndDelete(subscriber : Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true;
        subscriberToDelete = subscriber
        saveButton.value = "Update"
        clearButton.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
package com.ifyezedev.contactsproject.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ifyezedev.contactsproject.Contact
import com.ifyezedev.contactsproject.ContactRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository = ContactRepository(application)

    private val allContacts: LiveData<List<Contact>>?

    private val searchResults: MutableLiveData<List<Contact>>

    private val allContactsASCResult: MutableLiveData<List<Contact>>

    private val allContactsDESCResult: MutableLiveData<List<Contact>>

    init {
        allContacts = repository.allContacts
        searchResults = repository.searchResults
        allContactsASCResult = repository.allContactsASC
        allContactsDESCResult = repository.allContactsDESC
    }

    fun insertContact(contact: Contact) {
        repository.insertContact(contact)
    }
    fun findContact(name: String) {
        repository.findProduct(name)
    }
    fun deleteContact(id: Int) {
        repository.deleteContact(id)
    }
    fun getSearchResults(): MutableLiveData<List<Contact>> {
        return searchResults
    }
    fun getAllContacts(): LiveData<List<Contact>>? {
        return allContacts
    }
    fun getAllContactsASC(){
        repository.getAllContactsASC()
    }
    fun getAllContactsDESC(){
        repository.getAllContactsDESC()
    }
    fun getAllContactsASCResult(): MutableLiveData<List<Contact>> {
        return allContactsASCResult
    }
    fun getAllContactsDESCResult(): MutableLiveData<List<Contact>> {
        return allContactsDESCResult
    }

}
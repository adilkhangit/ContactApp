package com.example.assignment.adil.adilkhanassignment.chatscreen

import Contact
import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.adil.projectassignment.Utility.NetworkCheck.isNetworkAvailable
import com.example.assignment.adil.projectassignment.network.RetrofitInstance
import com.google.firebase.Firebase
import com.google.firebase.perf.performance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ContactViewModel(private val contactRepository: ContactRepository, context: Activity) : ViewModel() {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())

    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)

    val toastMessage: StateFlow<String?> = _toastMessage


    init {
        viewModelScope.launch(Dispatchers.IO) {
            if(isNetworkAvailable(context)) fetchAndSaveContacts()
           fetchFromDb()
        }
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun fetchFromDb(){
        contactRepository.allContacts.collect { contactList ->
            _contacts.value = contactList
            _isLoading.value = false
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.insert(contact)
            _toastMessage.value = "Contact Added"
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO){
            contactRepository.deleteContact(contact.id)
            _toastMessage.value = "Contact Deleted"
            fetchFromDb()
        }
    }


    fun getContactById(contactId: Int): Contact? {
        return contacts.value.find { it.id == contactId }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.update(contact)
            _toastMessage.value = "Contact Updated"
            fetchFromDb()
        }
    }

    suspend fun fetchAndSaveContacts() {
        val trace = Firebase.performance.newTrace("custom_network_trace")
        trace.start()
            try {
                val response = RetrofitInstance.api.getRandomUsers(50)
                val newContacts = response.results.map { user ->
                    Contact(name = user.name.first, surname = user.name.last, phone = user.phone)
                }
                contactRepository.deleteOldContacts()
                contactRepository.insertContacts(newContacts)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        trace.stop()
        }

    fun clearToast() {
        _toastMessage.value = null
    }
}

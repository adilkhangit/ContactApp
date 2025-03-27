package com.example.assignment.adil.adilkhanassignment.chatscreen

import kotlinx.coroutines.flow.Flow


class ContactRepository(private val contactDao: ContactDao) {
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact) {
        contactDao.insertContact(contact)
    }
    suspend fun insertContacts(contact: List<Contact>) {
        contactDao.insertContacts(contact)
    }

    suspend fun deleteOldContacts() {
        contactDao.deleteOldContacts()
    }

    suspend fun deleteContact(id: Int){
        contactDao.deleteContact(id)
    }

    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }
}

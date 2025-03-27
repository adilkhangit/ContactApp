package com.example.assignment.adil.projectassignment.screen

import Contact
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.assignment.adil.adilkhanassignment.chatscreen.ContactViewModel
import com.google.firebase.analytics.FirebaseAnalytics


@Composable
fun ContactListScreen(
    navController: NavController,
    viewModel: ContactViewModel,
    firebaseAnalytics: FirebaseAnalytics
) {
    val contacts by viewModel.contacts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val toastMessage by viewModel.toastMessage.collectAsState()

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Contacts",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
        )
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("addContact") }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (contacts.isEmpty()) {
                    Text(text = "You have no contacts yet", fontSize = 18.sp, color = Color.Gray)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = contacts,
                            key = { it.id }
                        ) { contact ->
                            SwipeToDelete(contact = contact, onDelete = {
                                viewModel.deleteContact(contact)
                                firebaseAnalytics.logEvent("Task_Deleted", null)
                            },
                                onEdit = { selectedContact ->
                                navController.navigate("editContact/${selectedContact.id}")
                            }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SwipeToDelete(
    contact: Contact,
    onDelete: (Contact) -> Unit,
    onEdit: (Contact) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "swipe_animation")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (offsetX < -150f) Color.Red else MaterialTheme.colorScheme.surface)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX < -200f) {
                            onDelete(contact)
                        }
                        offsetX = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                    }
                )
            }
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.offset(x = animatedOffsetX.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = contact.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = contact.phone, fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Call Contact")
            }
            IconButton(onClick = { onEdit(contact) }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Contact",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
    navController: NavController,
    contactId: Int? = null,
    viewModel: ContactViewModel,
    firebaseAnalytics: FirebaseAnalytics
) { val contactToEdit = contactId?.let { viewModel.getContactById(it) } // Fetch if editing
    var name by remember { mutableStateOf(contactToEdit?.name ?: "") }
    var surname by remember { mutableStateOf(contactToEdit?.surname ?: "") }
    var phone by remember { mutableStateOf(contactToEdit?.phone ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Contact") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val contactUi = ContactUi(id = contactId?:0, name = name, surname = surname, phone = phone, userFlag = true)
                        if (name.isNotBlank() && phone.isNotBlank() && contactId == null) {
                            viewModel.addContact(contactUi.toContact())
                        }
                        else {
                            viewModel.updateContact(contactUi.toContact())
                            firebaseAnalytics.logEvent("Task_Edited", null)
                        }
                        navController.popBackStack()

                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save Contact")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Surname") },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

data class ContactUi(val id: Int, val name: String, val surname: String, val phone: String, val userFlag: Boolean) {
    fun toContact(): Contact {
        return Contact(id= id, name = name, surname = surname, phone= phone, userFlag = userFlag)
    }
}

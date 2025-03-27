package com.example.assignment.adil.projectassignment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.adil.adilkhanassignment.chatscreen.ContactRepository
import com.example.assignment.adil.adilkhanassignment.chatscreen.ContactViewModel
import com.example.assignment.adil.adilkhanassignment.chatscreen.ContactViewModelFactory
import com.example.assignment.adil.projectassignment.screen.AddContactScreen
import com.example.assignment.adil.projectassignment.screen.ContactListScreen
import com.example.assignment.adil.projectassignment.database.ContactDatabase
import com.example.assignment.adil.projectassignment.ui.theme.ProjectAssignmentTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = ContactDatabase.getInstance(this)
        val repository = ContactRepository(database.contactDao())
        val viewModel = ViewModelProvider(
            this,
            ContactViewModelFactory(repository, this)
        )[ContactViewModel::class.java]
        firebaseAnalytics = Firebase.analytics
       // testCrash()
        firebaseAnalytics.logEvent("app_started", null)
        setContent {
            val navController = rememberNavController()
            Scaffold {
                NavHost(
                    navController = navController,
                    startDestination = "contactList"
                ) {
                    composable("contactList") { ContactListScreen(navController, viewModel, firebaseAnalytics) }
                    composable("addContact") { AddContactScreen(navController, null, viewModel, firebaseAnalytics) }
                    composable("editContact/{contactId}") { backStackEntry ->
                        val contactId = backStackEntry.arguments?.getString("contactId")?.toIntOrNull()
                        contactId?.let { id ->
                            AddContactScreen(navController, id, viewModel, firebaseAnalytics)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjectAssignmentTheme {
        Greeting("Android")
    }
}


fun testCrash() {
    FirebaseCrashlytics.getInstance().log("Test crash initiated")
    throw RuntimeException("This is a test crash") // Force a crash
}

fun checkCrashlytics() {
    val crashlytics = FirebaseCrashlytics.getInstance()
    Log.d("Crashlytics", "Crashlytics Initialized: ${crashlytics.didCrashOnPreviousExecution()}")
}
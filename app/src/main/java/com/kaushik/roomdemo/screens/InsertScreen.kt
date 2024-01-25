package com.kaushik.roomdemo.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kaushik.roomdemo.data.AppDatabase
import com.kaushik.roomdemo.data.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun InsertScreen(db: AppDatabase) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var firstName by rememberSaveable {
            mutableStateOf("")
        }
        var lastName by rememberSaveable {
            mutableStateOf("")
        }

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") }
        )

        Button(
            onClick = {
                if (firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show()
                    return@Button
                } else {
                    var isDone = false
                    val count = sharedPref.getInt("count", 0)
                    val user = User(
                        count + 1,
                        firstName = firstName,
                        lastName = lastName
                    )
                    GlobalScope.launch {
                        db.userDao().insertAll(
                            user
                        )
                        sharedPref.edit().putInt("count", count + 1).commit()
                        isDone = true
                    }
                    while (!isDone);
                    firstName = ""
                    lastName = ""
                    Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "insert")
        }
    }
}
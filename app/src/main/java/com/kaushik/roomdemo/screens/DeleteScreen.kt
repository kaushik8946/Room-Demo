package com.kaushik.roomdemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kaushik.roomdemo.data.AppDatabase
import com.kaushik.roomdemo.data.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun DeleteScreen(db: AppDatabase) {
    LazyColumn {
        items(userslist.value) {
            UserItem(db, it)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun UserItem(db: AppDatabase, user: User) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = user.uid.toString())
            Text(text = user.firstName.toString())
            Text(text = user.lastName.toString())
        }
        IconButton(
            onClick = {
                GlobalScope.launch {
                    db.userDao().delete(user)
                    userslist.value = db.userDao().getAll()
                }
            }
        ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
        }
    }
}

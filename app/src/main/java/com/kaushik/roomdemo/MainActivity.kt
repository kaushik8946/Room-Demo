package com.kaushik.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.kaushik.roomdemo.data.AppDatabase
import com.kaushik.roomdemo.screens.DeleteScreen
import com.kaushik.roomdemo.screens.HomeScreen
import com.kaushik.roomdemo.screens.InsertScreen
import com.kaushik.roomdemo.screens.ListUsers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db by lazy {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "users.db"
            ).build()
        }

        val sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE)
        sharedPref.edit().putInt("id", 0).commit()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(navController)
                }
                composable("insert") {
                    InsertScreen(db)
                }
                composable("list") {
                    ListUsers(db)
                }
                composable("delete") {
                    DeleteScreen(db)
                }
            }
        }
    }
}

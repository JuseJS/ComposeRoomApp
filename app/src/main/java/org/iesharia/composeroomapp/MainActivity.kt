package org.iesharia.composeroomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.iesharia.composeroomapp.ui.theme.ComposeRoomAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeRoomAppTheme {
                val database = AppDatabase.getDatabase(this)
                TaskApp(database)
            }
        }
    }
}


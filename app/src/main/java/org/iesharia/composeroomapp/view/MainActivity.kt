package org.iesharia.composeroomapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import org.iesharia.composeroomapp.data.AppDatabase
import org.iesharia.composeroomapp.ui.theme.ComposeRoomAppTheme
import org.iesharia.composeroomapp.viewmodel.TaskViewModel
import org.iesharia.composeroomapp.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeRoomAppTheme {
                val database = AppDatabase.getDatabase(this)
                val viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(database.taskDao()))
                TaskApp(viewModel)
            }
        }
    }
}

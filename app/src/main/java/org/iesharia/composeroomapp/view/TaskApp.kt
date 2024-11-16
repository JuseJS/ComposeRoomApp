package org.iesharia.composeroomapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import org.iesharia.composeroomapp.data.AppDatabase
import org.iesharia.composeroomapp.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskApp(database: AppDatabase) {
    val taskDao = database.taskDao()
    val coroutineScope = rememberCoroutineScope()

    // Estado para la lista de tareas
    var tasks by remember { mutableStateOf(emptyList<Task>()) }

    // Estado para el nombre de la nueva tarea
    var newTaskName by remember { mutableStateOf("") }

    LaunchedEffect(taskDao) {
        taskDao.getAllTasks().collectLatest { taskList ->
            tasks = taskList
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task Manager", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Agregar nueva tarea
            OutlinedTextField(
                value = newTaskName,
                onValueChange = { newTaskName = it },
                label = { Text("New Task") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (newTaskName.isNotBlank()) {
                        coroutineScope.launch {
                            val newTask = Task(name = newTaskName)
                            taskDao.insert(newTask)
                            newTaskName = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Task")
            }

            Spacer(modifier = Modifier.height(16.dp))
            tasks.forEach { task ->
                TaskItem(task = task, onDelete = {
                    coroutineScope.launch {
                        taskDao.delete(task.id)
                    }
                })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(task.name, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Task")
            }
        }
    }
}

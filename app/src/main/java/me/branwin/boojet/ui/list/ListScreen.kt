package me.branwin.boojet.ui.list

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.branwin.boojet.viewmodels.MainViewModel
import me.branwin.boojet.viewmodels.MainViewModelFactory

@Composable
fun ListScreen() {
    val vm = MainViewModelFactory(LocalContext.current.applicationContext as Application)
        .create(MainViewModel::class.java)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        vm.allEntries.collectAsState().value.map {
            Text("Name: ${it.name}  Amount: ${it.amount}")
        }
    }
}
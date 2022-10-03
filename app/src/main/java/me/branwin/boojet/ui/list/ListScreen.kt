package me.branwin.boojet.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.branwin.boojet.viewmodels.MainViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
val moneyFormat: NumberFormat = NumberFormat.getCurrencyInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(vm: MainViewModel) {
    val entries = vm.allEntries.collectAsState().value
    Column(Modifier.fillMaxWidth()) {
        ElevatedCard(
            Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Total", fontSize = 24.sp)
                Text(moneyFormat.format(entries.sumOf { it.entry.amount.toDouble() }), fontSize = 24.sp)
            }
        }
        LazyColumn(Modifier.fillMaxWidth()) {
            items(entries) {
                ElevatedCard(
                    Modifier.fillMaxWidth().padding(10.dp)
                ) {
                    Column(Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(it.entry.name, fontSize = 20.sp)
                            Text(moneyFormat.format(it.entry.amount), fontSize = 24.sp)
                        }
                        Row {
                            Text(dateFormat.format(it.entry.timestamp))
                        }
                        Row {
                            it.categories.map {
                                InputChip(
                                    selected = true,
                                    onClick = { /*TODO*/ },
                                    label = { Text(it.name)},
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
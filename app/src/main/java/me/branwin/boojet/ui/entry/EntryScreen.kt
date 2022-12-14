@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package me.branwin.boojet.ui.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.branwin.boojet.R
import me.branwin.boojet.data.Category
import me.branwin.boojet.data.EntryWithCategories
import me.branwin.boojet.navigation.Entry
import me.branwin.boojet.ui.shared.NextNumberKeyboard
import me.branwin.boojet.viewmodels.MainViewModel

@Composable
fun EntryScreen(
    vm: MainViewModel,
    snackbarHostState: SnackbarHostState,
    entryViewModel: EntryViewModel = viewModel(),
    focusManager: FocusManager = LocalFocusManager.current
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            CategorySelectionSheet(
                vm.allCategories.collectAsState().value,
                selectedCategoryIds = entryViewModel.selectedCategories.map { it.categoryId },
                onNewCategory = {
                    val newCategory = Category(name = it)
                    vm.insertCategory(newCategory)
                    entryViewModel.addCategory(newCategory)
                },
                onSelectionChange = { category: Category, isAdd: Boolean ->
                    if (isAdd) entryViewModel.addCategory(category)
                    else entryViewModel.removeCategory(category)
                }
            )
       },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // State
            var name by remember { mutableStateOf("") }
            var amountInput by remember { mutableStateOf("") }

            // Heading
            Text(
                stringResource(Entry.label),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(16.dp))

            // Name
            TextField(
                name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.entry_name), modifier = Modifier.fillMaxWidth()) },
                singleLine = true,
            )

            // Amount
            TextField(
                amountInput,
                onValueChange = { amountInput = it },
                label = { Text(stringResource(R.string.entry_amount), modifier = Modifier.fillMaxWidth()) },
                textStyle = TextStyle(textAlign = TextAlign.End),
                singleLine = true,
                keyboardOptions = NextNumberKeyboard,
            )

            // Category Selection
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus()
                    scope.launch {
                        sheetState.show()
                    }
                },
            ) {
                Text("Add Categories")
            }

            // Selected Categories
            Row(
                Modifier.horizontalScroll(rememberScrollState())
            ) {
                entryViewModel.selectedCategories.map {
                    InputChip(
                        selected = true,
                        onClick = { entryViewModel.removeCategory(it) },
                        label = { Text(it.name)},
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            // Clear & Save
            ActionRow(
                onClear = {
                    name = ""
                    amountInput = ""
                    entryViewModel.reset()
                    focusManager.clearFocus()
                },
                onSave = {
                    vm.insertEntry(EntryWithCategories(
                        entry = me.branwin.boojet.data.Entry(name = name, amount = amountInput.toFloatOrNull() ?: 0.0f),
                        categories = entryViewModel.selectedCategories.toMutableList()
                    ))
                    name = ""
                    amountInput = ""
                    entryViewModel.reset()
                    focusManager.clearFocus()
                    scope.launch {
                        snackbarHostState.showSnackbar("Entry saved")
                    }
                }
            )
        }
    }
}

@Composable
fun ActionRow(onClear: () -> Unit, onSave: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(onClick = onClear) {
            Text("Clear")
        }

        Button(onClick = onSave) {
            Text("Save")
        }
    }
}

@Composable
fun CategorySelectionSheet(
    allCategories: List<Category>?,
    selectedCategoryIds: List<Long> = listOf(),
    onNewCategory: (newCategoryName: String) -> Unit,
    onSelectionChange: (category: Category, isAdd: Boolean) -> Unit,
) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        LazyColumn(Modifier.fillMaxWidth()) {
            item {
                Text(
                    stringResource(R.string.pick_category),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 10.dp)
                        .fillMaxWidth(),
                    fontSize = 24.sp
                )
            }

            allCategories?.let {
                items(allCategories) {
                    var checked = selectedCategoryIds.contains(it.categoryId)
                    val category = it
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .toggleable(
                                value = checked,
                                onValueChange = {
                                    checked = !checked
                                    onSelectionChange(category, checked)
                                },
                                role = Role.Checkbox
                            ),
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = null, // Google recommends for screen ready accessibility
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(it.name)
                    }
                }
            }
        }

        CategoryAdder(onNewCategory = onNewCategory)
    }
}

@Composable
fun CategoryAdder(
    focusManager: FocusManager = LocalFocusManager.current,
    onNewCategory: (newCategoryName: String) -> Unit
) {
    var categoryName by remember { mutableStateOf("") }

    TextField(
        categoryName,
        onValueChange = { categoryName = it },
        label = { Text(stringResource(R.string.new_cat_name)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onNewCategory(categoryName)
            categoryName = ""
            focusManager.clearFocus()
        })
    )
}